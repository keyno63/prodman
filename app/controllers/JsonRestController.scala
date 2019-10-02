package controllers

import domains.{HtmlResource, Resource, Response}
import javax.inject._
import play.api.mvc._
import play.api.libs.circe.Circe
import io.circe.{HCursor, Json}
import io.circe.syntax._
import io.circe.generic.auto._
import io.circe.parser.{parse => cparse}

import scala.concurrent.{ExecutionContext, Future}

class JsonRestController @Inject()(
    cc: ControllerComponents
    )(implicit ec: ExecutionContext) extends AbstractController(cc) with Circe {

  val logger = play.api.Logger("json_controller")

  def json: Action[AnyContent] = Action.async { implicit request =>
    Future(
      Ok(Response(200, "success").asJson)
    )
  }

  def htmlResource(value :Option[String]): Action[AnyContent] = Action.async { implicit request =>
    Future {
      val resource = value match {
        case Some(x) => Resource.getResources(x)
        case _ => Left(new Exception("shortage resource value."))
      }
      resource match {
        case Right(str) => Ok(HtmlResource(200, str).asJson)
        case Left(e) => BadRequest(HtmlResource(400, e.getMessage).asJson)
      }
    }
  }

  def matching(): Action[Json] = Action(circe.json) async { implicit request =>
    val r1 = """(\d{4})""".r
    val r2 = """(\d{4})-(\d{2})-(\d{2})""".r
    Future{
      logger.info(s"name parameter: ${request.body.toString()}")
      cparse(request.body.toString()) match {
        case Right(json) =>
          val cursor: HCursor = json.hcursor
          val ret = cursor.downField("time").as[String]
          ret match {
            case r1(a) =>
              Ok(Response(200, s"year: $a").asJson)
            case t @ r2(a, b, c) =>
              Ok(Response(200, s"${json.toString}: $a, $b, $c").asJson)
            case _ => InternalServerError(Response(500, s"failed matched pattern. ${json.toString}").asJson)
          }
        case _ =>
          BadRequest(Response(400, "").asJson)
      }
    }
  }

  def create(): Action[Json] = Action(circe.json(1024)) async { implicit request =>
    Future {
      val x = request.body.asJson
      logger.info(s"name parameter: $x")
      Ok(x)
        .withHeaders(
          CACHE_CONTROL -> "max-age=3600",
          ETAG -> "add_etag",
          "ORGI" -> "orig"
        )
    }
  }

  def rawJson(): Action[AnyContent] = Action.async { implicit request =>
    Future {
      logger.info("rawJson.")


      val res = cparse(
        """
          |{
          |  "part1": {
          |    "part2": true,
          |    "str": "sample"
          |  },
          |  "str0": "sample0",
          |  "dic1": {
          |    "dic2": {
          |      "dic3": {
          |        "list0": [
          |          {
          |            "listDig": "data",
          |            "listDig1": "data1",
          |            "listDig2": "data2"
          |          },
          |          {
          |            "listDig": "data0",
          |            "listDig1": "data01",
          |            "listDig2": "data02"
          |          },
          |          {
          |            "listDig": "data1",
          |            "listDig1": "data11",
          |            "listDig2": "data12"
          |          }
          |        ]
          |      }
          |    }
          |  }
          |}
          |""".stripMargin)
      res match {
        case Right(value) => Ok {
          value
        }
        case _ => BadRequest("")
      }
    }
  }
}
