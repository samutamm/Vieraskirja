package controllers

import java.lang.ProcessBuilder.Redirect

import play.api.mvc.{Action, Controller}
import play.api.libs.json._
import models.Viesti
import play.api.data.Form
import play.api.data.Forms.{mapping, text, of}
import play.api.data.format.Formats.doubleFormat
import play.api.data.validation.Constraints

case class CreateViesti(nimi: String, sisalto: String)

object CreateViesti {
  val form = Form(mapping(
    "nimi" -> text.verifying(Constraints.nonEmpty),
    "sisalto" -> text.verifying(Constraints.nonEmpty)
  )(CreateViesti.apply)(CreateViesti.unapply))
}

object Viestit extends Controller {

  val vieraskirja = models.Vieraskirja

  implicit val writesViesti = Json.writes[Viesti]

  val list = Action { implicit request =>
    val viestit = vieraskirja.list()
    render {
      case Accepts.Html() => Ok(views.html.list(viestit))
      case Accepts.Json() => Ok(Json.toJson(viestit))
    }
  }

  val create = Action { implicit request =>
    CreateViesti.form.bindFromRequest().fold(
      formWithErrors => render {
        case Accepts.Html() => BadRequest(views.html.createForm(formWithErrors))
        case Accepts.Json() => BadRequest(formWithErrors.errorsAsJson)
      },
      createViesti => {
        vieraskirja.create(createViesti.nimi, createViesti.sisalto) match {
          case Some(viesti) => render {
            case Accepts.Html() => Redirect(routes.Viestit.details(viesti.id))
            case Accepts.Json() => Ok(Json.toJson(viesti))
          }
          case None => InternalServerError
        }
      }
    )
  }

  val createForm = Action {
    Ok(views.html.createForm(CreateViesti.form))
  }

  def details(id: Long) = Action { implicit request =>
    vieraskirja.get(id) match {
      case Some(viesti) => render {
        case Accepts.Html() => Ok(views.html.details(viesti))
        case Accepts.Json() => Ok(Json.toJson(viesti))
      }
      case None => NotFound
    }
  }


  def update(id: Long) = Action { implicit request =>
    CreateViesti.form.bindFromRequest().fold(
      formWithErrors => BadRequest(formWithErrors.errorsAsJson),
      updateViesti => vieraskirja.update(id, updateViesti.nimi, updateViesti.sisalto) match {
        case Some(viesti) => Ok(Json.toJson(viesti))
        case None => InternalServerError
      }
    )
  }

  def delete(id: Long) = Action {
    vieraskirja.delete(id)
    Redirect(routes.Viestit.list)
  }

}