package controllers.sample

import java.time.OffsetDateTime

import domains.sample.PostPgsqlRepository
import form.Post
import javax.inject.Inject
import play.api.data.Form
import play.api.data.Forms.{mapping, text}
import play.api.i18n.{I18nSupport, Messages, MessagesApi}
import play.api.mvc.{AbstractController, Action, AnyContent, ControllerComponents}

class DBController @Inject()(cc: ControllerComponents, override val messagesApi: MessagesApi)
  extends AbstractController(cc)  with I18nSupport {

  case class PostRequests(body: String)
  import play.api.libs.json.{JsValue, Json, Writes}

  case class Meta(status: Int, errorMessage: Option[String] = None)

  object Meta {
    implicit val writes: Writes[Meta] = Json.writes[Meta]
  }

  case class Response(meta: Meta, data: Option[JsValue] = None)

  object Response {
    implicit def writes: Writes[Response] = Json.writes[Response]
  }

  private[this] val form = Form(
    mapping(
      "post" -> text(minLength = 1, maxLength = 10)
    )(PostRequests.apply)(PostRequests.unapply))

  def get: Action[AnyContent] = Action { implicit request =>
    Ok(Json.toJson(Response(Meta(200), Some(Json.obj("posts" -> Json.toJson(PostPgsqlRepository.findAll))))))
  }

  def post: Action[AnyContent] = Action { implicit request =>
    form.bindFromRequest.fold(
      error => {
        val errorMessage = Messages(error.errors("post").head.message)
        BadRequest(
          Json.toJson(
            Response(
              Meta(400),
              Some(
                Json.obj("error" -> Json.toJson(errorMessage))
              )
            )
          )
        )
      },
      postRequests => {
        val post = Post(postRequests.body, OffsetDateTime.now)
        PostPgsqlRepository.add(post)
        Ok(
          Json.toJson(Response(Meta(200)))
        )
      }
    )
  }

}
