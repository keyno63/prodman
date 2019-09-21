package domains

import scala.io.Source

object Resource {
  def getResources(value: String): Either[Exception, String] = {
    val resource = getFilePath(value)
    try {
      val file = Source.fromFile(resource)
      val ret = Right(file.getLines.mkString)
      file.close
      ret
    } catch {
      case e: Exception => Left(new Exception(e.getMessage))
    }
  }

  private def getFilePath(value: String) = value match {
    case _ => "resources/html/base.html"
  }
}

