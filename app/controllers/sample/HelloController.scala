package controllers.sample

import javax.inject.{Inject, Singleton}
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.mvc.{AbstractController, _}

@Singleton
class HelloController @Inject()(cc: ControllerComponents)
  extends AbstractController(cc) with I18nSupport {

  def get(name: Option[String]): Action[AnyContent] =
    Action { implicit request: Request[AnyContent] =>
      Ok {
        name
          .map(s => Messages("hello", s))
          .getOrElse(Messages("noQuery"))
      }
    }

  def plus(a: Option[Int], b: Option[Int]): Action[AnyContent] =
    Action { implicit request: Request[AnyContent] =>
      val ret = (a, b) match {
        case (Some(x), Some(y)) => s"${x+y}"
        case _ => "Please give arguments of a and b."
      }
      Ok(ret)
    }
}