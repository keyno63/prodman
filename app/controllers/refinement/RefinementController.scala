package controllers.refinement

import io.circe.Json
import play.api.mvc._
import javax.inject._
import play.api.libs.circe.Circe
import io.circe.syntax._
import io.circe.generic.auto._
import services.refinement.RefinementService

import scala.concurrent.{ExecutionContext, Future}

class RefinementController @Inject()(
                                    cc: ControllerComponents,
                                    rs: RefinementService
                                  )(implicit ec: ExecutionContext) extends AbstractController(cc) with Circe {

  def start(story: Option[String], userIds: Option[String]): Action[AnyContent] = Action.async { implicit request =>
    Future {
      (story, userIds) match {
        case (Some(x), Some(y)) =>
          rs.start(x.toString, y.toString.split(","))
          Ok("Ok")
        case (_, _) => BadRequest("Failed")
      }
    }
  }

  def point(userId: Option[String], point: Option[Int]): Action[AnyContent] = Action.async { implicit request =>
    Future {
      (userId, point) match {
        case (Some(x), Some(y))=>
          val ret = rs.setPoint(x, y)
          ret match {
            case Right(r) =>
              Ok(x)
            case Left(l) =>
              BadRequest(l)
          }
        case (_, _) =>
          BadRequest("invalid keyparams")
      }
    }
  }

  def status(): Action[AnyContent] = Action.async { implicit request =>
    Future {
      rs.getState() match {
        case Right(r) => Ok(r.asJson)
        case Left(l) => BadRequest(Json.obj("reason" -> l.asJson))
      }
    }
  }

}
