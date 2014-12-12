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


    "lisaa viesti JSON:illa" in new VieraskirjaApplication {
      val vastaus = call(Viestit.create, FakeRequest().withJsonBody(luoViesti))
      status(vastaus) must equalTo (OK) //tutki tämä!
      contentAsJson(vastaus) must equalTo (luotuViesti)
    }
    
    

  }

}
