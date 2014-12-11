package models

import scala.concurrent.stm.{Ref, atomic}
import scala.collection.immutable.SortedMap

case class Viesti(id: Long, nimi: String, sisalto: String)

trait Vieraskirja {

  def list(): Iterable[Viesti]

  def create(nimi: String, sisalto: String): Option[Viesti]

  def get(id: Long): Option[Viesti]

  def update(id: Long, nimi: String, sisalto: String): Option[Product]

  def delete(id: Long): Boolean

}

object Vieraskirja extends Vieraskirja {

  import db.Schema.{ds, viestit}
  import db.Schema.queryLanguage._
  import play.api.Play.current

  def list(): Iterable[Viesti] = ds withSession { implicit session =>
    viestit.list()
  }

  def create(nimi: String, sisalto: String): Option[Viesti] = ds withSession { implicit session =>
    val id = viestit.returning(viestit.map(_.id)) += Viesti(0, nimi, sisalto)
    viestit.byId(id).firstOption()
  }

  def get(id: Long): Option[Viesti] = ds withSession { implicit session =>
    viestit.byId(id).firstOption()
  }

  def update(id: Long, nimi: String, sisalto: String): Option[Viesti] = ds withSession { implicit session =>
    viestit.byId(id).update(Viesti(id, nimi, sisalto))
    viestit.byId(id).firstOption()
  }

  def delete(id: Long): Boolean = ds withSession { implicit session =>
    viestit.byId(id).delete != 0
  }

}
