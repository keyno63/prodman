package refinement.domain

case class UserAccount(id: String) {
  private var point = 0
  private var defined = false

  def setPoint(point: Int): UserAccount = {
    this.point = point
    this.defined = true
    this
  }

  def getPoint: Int = this.point

  def isDefined: Boolean = this.defined

}

case class ResponseUser(id: String, point: Int)