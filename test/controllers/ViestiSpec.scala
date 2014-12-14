package controllers

import models.Vieraskirja
import play.api.test.{PlaySpecification, FakeRequest}
import play.api.libs.json.Json
import vieraskirja.VieraskirjaApplication

class ViestiSpec extends PlaySpecification {

  "Viesti controllerin" should {

    val luoViesti = Json.obj("nimi" -> "API testausta", "sisalto" -> "jeeee")
    val luotuViesti = Json.obj("id" -> 1, "nimi" -> "API testausta", "sisalto" -> "jeeee")

    "lataa etusivu" in new VieraskirjaApplication {
      val tulos = controllers.Application.index (FakeRequest())

      status(tulos) must equalTo(OK)
      contentType(tulos) must beSome("text/html")
    }


    "lisaa viesti JSON:illa, seuraa uudelleenohjaus" in new VieraskirjaApplication {
      val vastaus = controllers.Viestit.create(FakeRequest().withJsonBody(luoViesti))
      status(vastaus) must equalTo (SEE_OTHER)
    }
    
    

  }

}
