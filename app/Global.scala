import models.Vieraskirja
import play.api.{Application, GlobalSettings}

object Global extends GlobalSettings {

  override def onStart(app: Application): Unit = {
    super.onStart(app)
//    if (Vieraskirja.list.isEmpty) {
//      Vieraskirja.create("Samu", "Bienvenue, mes madammes, mes monsieurs!")
//    }
  }

}
