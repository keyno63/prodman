package controllers.refinement

import javax.inject.Inject
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.i18n.{I18nSupport, MessagesApi}
import play.api.mvc.{AbstractController, ControllerComponents}
import refinement.repository.PostRepository


case class PostRequest(body: String)

class DbRepositoryController @Inject()(cc: ControllerComponents, override val messagesApi: MessagesApi)
  extends AbstractController(cc) with I18nSupport {
  private[this] val form = Form(
    mapping(
      "post" -> text(minLength = 1, maxLength = 10).withPrefix("hogeika")
    )(PostRequest.apply)(PostRequest.unapply))
  // GET action.
  def get = Action { implicit request =>
    Ok(views.html.index(PostRepository.findAll, form))
  }
}
