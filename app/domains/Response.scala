package domains

case class Response(code: Int, reason: String)
case class HtmlResource(code: Int, html: String)