package controllers

import javax.inject.Inject
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents, Request}

import scala.concurrent.{ExecutionContext, Future}

class HtmlTextResponder @Inject()(
    cc: ControllerComponents
    )(implicit ec: ExecutionContext) extends AbstractController(cc) {

  val logger = play.api.Logger("html_text_response")

  def html: Action[AnyContent] = Action.async { implicit request =>
    Future(
      Ok{
        """<!DOCTYPE html>
          |<html lang="en">
          |  <head>
          |    <meta charset="UTF-8">
          |    <title>Title</title>
          |  </head>
          |  <body>
          |    hoge
          |  </body>
          |</html>
          |""".stripMargin
      }.as(HTML)
    )
  }

}
