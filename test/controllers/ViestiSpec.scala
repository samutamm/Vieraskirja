package controllers

import play.api.test.{PlaySpecification, FakeRequest}
import play.api.libs.json.Json
import vieraskirja.VieraskirjaApplication

class ViestiSpec extends PlaySpecification {

  "Viesti controllerin" should {

    val luoViesti = Json.obj("nimi" -> "API testausta", "sisalto" -> "jeeee")
    val luotuViesti = Json.obj("id" -> 1, "nimi" -> "API testausta", "sisalto" -> "jeeee")

    //Tama feilaa enka ehdi nyt testata muita metodeita.. :/
    "lisaa viesti JSON:illa" in new VieraskirjaApplication {
      val response = call(Viestit.create, FakeRequest().withJsonBody(luoViesti))
      status(response) must equalTo (OK)
      contentAsJson(response) must equalTo (luotuViesti)
    }

  }

}
