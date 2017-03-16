package main.address

import main.common.PersonName
import main.common.EmailAddress
import main.common.PhoneNumber
import play.api.libs.json._
import play.api.libs.functional.syntax._

object ContactQueryMapper {

  object TableMapping {
    val id = "ID"
    val firstName = "FIRST_NAME"
    val lastName = "LAST_NAME"
    val email = "EMAIL"
    val phone = "PHONE"
    val deleted = "DELETED"
  }
  
  object JsonMapping {
    val writes = new Writes[Contact] {
      def writes(contact: Contact) = Json.obj(
        "id" -> contact.id.value,
        "firstName" -> contact.name.firstName,
        "lastName" -> contact.name.lastName,
        "email" -> contact.email.value,
        "phone" -> contact.phone.value
      )
    }
    
    val reads = (
      (__ \ "id").read[String] and
      (__ \ "firstName").read[String] and
      (__ \ "lastName").read[String] and
      (__ \ "email").read[String] and
      (__ \ "phone").read[String] and
      (__ \ "deleted").read[Boolean]
    )(Contact.apply _)
  }

  /** 1行分のmapをContactクラスにマッピングします */
  private def mappingColumn(from: Map[String, Any]): Contact = {
    new Contact(
      new ContactId(from(TableMapping.id).asInstanceOf[String]),
      new PersonName(
        from(TableMapping.firstName).asInstanceOf[String],
        from(TableMapping.lastName).asInstanceOf[String]  
      ),
      new EmailAddress(from(TableMapping.email).asInstanceOf[String]),
      new PhoneNumber(from(TableMapping.phone).asInstanceOf[String]),
      from(TableMapping.deleted).asInstanceOf[Boolean]
    )
  }
  
  /** 1行分のContactクラスをJsonにシリアライズします */
  def serializeJsonColumn(from: Contact) = {
    implicit val writes = JsonMapping.writes
    Json.toJson(from)
  }
  
  /** マップのリストを受け取り、Contactオブジェクトを返します */
  def mapping(from: List[Map[String, Any]]): List[Contact] = from.map(mappingColumn(_))
  
  /** Contactのリストを受け取り、Jsonにシリアライズします */
  def serializeJson(from: List[Contact]) = from.map(serializeJsonColumn(_))
}