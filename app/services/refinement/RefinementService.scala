package services.refinement

import domains.refinement.{Group, Room, User}

class RefinementService {

  var r: Room = _

  def start(story: String, userIds: Seq[String]): Unit = {
    val l = userIds.map(User)
    r = new Room(story, l)
  }

  def setPoint(userId: String, point: Int): Either[String, Any] = {
    val ret = r.setPoint(userId, point)
    ret match {
      case Right(x) =>
        if (r.isDefined)
          sendPush()
        Right(x)
      case Left(x) => Left(x)
    }
  }

  def getState(): Either[String, Group] = {
    r.getResponse()
  }

  // TODO: impl later.
  private def sendPush(): Unit = {}
}
