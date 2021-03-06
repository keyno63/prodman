package refinement.service

import refinement.domain.{Group, Room, UserAccount}

class RefinementService {

  var r: Room = _

  def start(story: String, userIds: Seq[String]): Unit = {
    val l = userIds.map(UserAccount)
    r = new Room(story, l)
  }

  def setPoint(userId: String, point: Int): Either[String, String] = {
    val ret = r.setPoint(userId, point)
    ret.right.map{
      x =>
        if (r.isDefined)
          sendPush()
        x
    }
  }

  def getState: Either[String, Group] = {
    r.getResponse
  }

  // TODO: impl later.
  private def sendPush(): Unit = {}
}
