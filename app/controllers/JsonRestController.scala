package controllers

import domains.Response
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
}
