package refinement.domain

case class User(id: String) {
  private var point = 0
  private var defined = false

  def setPoint(point: Int): User = {
    this.point = point
    this.defined = true
    this
  }

  def getPoint: Int = this.point

  def isDefined: Boolean = this.defined

}

case class ResponseUser(id: String, point: Int)