package refinement.domain

class RoomManagement {
  var rooms: Seq[Room] = Seq()

  def start(story: String, members: Seq[User]): Either[String, String] = {
    findRoom(story) match {
      case x if x.isEmpty =>
        val r = new Room(story, members)
        this.rooms ++= Seq(r)
        Right(s"create Room. story=[$story], members=[${members.map(_.id).mkString(",")}]")
      case _ => Left(s"already exist room. name=[$story].")
    }
  }

  def setPoint(story: String, userId: String, point: Int): Either[String, String] = {
    findRoom(story) match {
      case Seq(x) => x.setPoint(userId, point)
      case _ => Left(s"not exist room=[$story].")
    }
  }

  def getState(story: String): Either[String, Group] = {
    findRoom(story) match {
      case Seq(x) => x.getResponse
      case _ => Left(s"not exist room=[$story].")
    }
  }

  private def findRoom(story: String): Seq[Room] = {
    this.rooms.filter(_.story == story)
  }

}
