package refinement.domain

class Room(val story: String, members: Seq[UserAccount]) {

  def isDefined: Boolean = {
    val definedMember = members.filter(!_.isDefined)
    definedMember.isEmpty
  }

  def setPoint(id: String, point: Int):Either[String, String] = {
    val n = members.filter(_.id == id)
      .map(_.setPoint(point))
    if (n.nonEmpty) Right("Success") else Left("Failed")
  }

  def getResponse: Either[String, Group] = {
    if (isDefined) {
      val ret = this.members
        .map(x => ResponseUser apply (x.id, x.getPoint))
      Right(Group(this.story, ret))
    } else {
      val m = members.filter(!_.isDefined).map(_.id).mkString(",")
      Left(s"not yet set. member=[$m].")
    }
  }

}

case class Group(story: String, members: Seq[ResponseUser])