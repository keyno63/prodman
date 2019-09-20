package controllers

import play.api.mvc._
import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import play.api.libs.circe.Circe
import io.circe.Json
import io.circe.syntax._



import scala.concurrent.{ExecutionContext, Future}

class JsonRestController @Inject()(
    cc: ControllerComponents
    )(implicit ec: ExecutionContext) extends AbstractController(cc) with Circe {

  def json: Action[AnyContent] = Action.async { implicit request =>
    Future(
      Ok(Json.obj("hello" -> "world".asJson))
    )
  }
}
