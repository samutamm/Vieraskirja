package db

import models.Viesti

object Schema {
  val queryLanguage = scala.slick.driver.H2Driver.simple
  import queryLanguage._
  import scala.slick.lifted.{Tag, TableQuery}

  class Viestit(tag: Tag) extends Table[Viesti](tag, "VIESTIT") {
    val id = column[Long]("ID", O.AutoInc)
    val nimi = column[String]("NIMI")
    val sisalto = column[String]("SISALTO")
    override def * = (id, nimi, sisalto) <> (Viesti.tupled, Viesti.unapply)
  }
  val viestit = TableQuery[Viestit]

  object Viestit {
    implicit class ItemsExtensions[A](val q: Query[Viestit, A]) {
      val byId = Compiled { (id: Column[Long]) =>
        q.filter(_.id === id)
      }
    }
  }

  def ds = Database.forDataSource(play.api.db.DB.getDataSource()(play.api.Play.current))
}
