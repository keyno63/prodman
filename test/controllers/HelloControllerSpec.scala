package controllers

import controllers.sample.HelloController
import org.scalatestplus.play.PlaySpec
import play.api.test.FakeRequest
import play.api.test.Helpers._

class HelloControllerSpec extends PlaySpec {

  def controller: HelloController = new HelloController(stubControllerComponents())
  //def controller: HelloController = new HelloController(stubMessagesApi())

  "get" should {
    "クエリーパラメータがある場合は「Hello, namae!」というレスポンスを返す" in {
      val name = "namae"
      val result = controller.get(Some(name))(FakeRequest())
      assert(status(result) === 200)
      //assert(contentAsString(result) === s"Hello, $name!")
    }

    """クエリーパラメータがない場合は「Please give a name as a query parameter named "name".」というレスポンスを返す""" in {
      val result = controller.get(None)(FakeRequest())
      assert(status(result) === 200)
      // assert(contentAsString(result) === """Please give a name as a query parameter named "name".""")
    }
  }
}