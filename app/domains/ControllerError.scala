package domains

sealed trait ControllerError
case class ParseError(reason: String) extends ControllerError
case class ArgmentError(reason: String) extends ControllerError
