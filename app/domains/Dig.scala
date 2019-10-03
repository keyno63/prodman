package domains

import io.circe._

case class Dig(var1: String, var2: String, var3: String)

object Dig {
  implicit val decoder: Decoder[Dig] = new Decoder[Dig] {
    final def apply(c: HCursor): Decoder.Result[Dig] =
      for {
        var1 <- c.downField("listDig").as[String]
        var2 <- c.downField("listDig1").as[String]
        var3 <- c.downField("listDig2").as[String]
      } yield {
        Dig(var1, var2, var3)
      }
  }
}