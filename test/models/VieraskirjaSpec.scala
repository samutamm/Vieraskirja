package models

import org.specs2.mutable.Specification
import vieraskirja.VieraskirjaApplication

class VieraskirjaSpec extends Specification {

  "Vieraskirja" should {

    val vieraskirja = Vieraskirja

    "lisaa viesti" in new VieraskirjaApplication {
      val nimi = "Jontti"
      val sisalto = "olen rontti"
      val viesti = vieraskirja.create(nimi, sisalto)
      viesti.isDefined === (true)
      viesti.get.sisalto must beEqualTo(sisalto)
      viesti.get.nimi must beEqualTo(nimi)
    }

    "listaa viestit" in new VieraskirjaApplication {
      vieraskirja.list must beEmpty
      vieraskirja.create("Heidi", "tere!")
      vieraskirja.create("Jorma", "moro!")
      vieraskirja.list must haveSize (2)
    }

    "hae viesti" in new VieraskirjaApplication {
      val hakutulos = for {
        luotuViesti <- vieraskirja.create("Kaakko", "Jaakko")
        viesti <- vieraskirja.get(luotuViesti.id)
      } yield viesti
      hakutulos must beSome (Viesti(1, "Kaakko", "Jaakko"))
    }

    "poista viesti" in new VieraskirjaApplication {
      val poistettava = for {
        luotu <- vieraskirja.create("Miguel", "Caramba!")
        poistettu = vieraskirja.delete(luotu.id)
      } yield (poistettu, vieraskirja.get(luotu.id))
      poistettava must beSome ((true, None))
    }

  }

}
