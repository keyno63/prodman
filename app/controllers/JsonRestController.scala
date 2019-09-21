package controllers

import domains.{HtmlResource, Resource, Response}
import play.api.mvc._
import javax.inject._
import play.api.libs.circe.Circe
import io.circe.syntax._
import io.circe.generic.auto._

import scala.concurrent.{ExecutionContext, Future}

class JsonRestController @Inject()(
    cc: ControllerComponents
    )(implicit ec: ExecutionContext) extends AbstractController(cc) with Circe {

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
}
