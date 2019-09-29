package controllers

import domains.{HtmlResource, Resource, Response}
import javax.inject._
import play.api.mvc._
import play.api.libs.circe.Circe
import io.circe.Json
import io.circe.syntax._
import io.circe.generic.auto._
import io.circe.parser._

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

  def create(): Action[Json] = Action(circe.json(1024)) async { implicit request =>
    Future{
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
}
