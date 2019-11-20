package domains.sample

import form.Post
import scalikejdbc._
import scalikejdbc.config.{DBs, DBsWithEnv}
import scalikejdbc.jsr310._

object PostPgsqlRepository {

  //DBsWithEnv("prod").setup('sandbox)
  DBs.setupAll()

  def findAll: Seq[Post] = NamedDB("pgsql") readOnly { implicit session =>
  //def findAll: Seq[Post] = DB readOnly { implicit session =>
    sql"SELECT name, date FROM company".map { rs =>
      Post(rs.long("id"), rs.string("body"), rs.offsetDateTime("date"))
    }.list().apply()
  }

  def add(post: Post): Unit = NamedDB("pgsql") localTx { implicit session =>
  //def add(post: Post): Unit = DB localTx { implicit session =>
    sql"INSERT INTO posts (body, date) VALUES (${post.body}, ${post.date})".update().apply()
  }

}