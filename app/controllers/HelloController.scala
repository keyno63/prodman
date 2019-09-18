package controllers

import javax.inject.{Inject, Singleton}
import play.api.i18n.{I18nSupport, Messages}
import play.api.mvc._

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
      Ok {
        val x = a.getOrElse(0) + b.getOrElse(0)
        s"$x"
      }
    }
}