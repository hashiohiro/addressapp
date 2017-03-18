package main.address

import play.api.libs.json._
import play.api.libs.functional.syntax._
import main.common.{ EmailAddress, PersonName, PhoneNumber }

object ContactQueryMapper {

  /**
   * データベースとオブジェクトのマッピングを提供する。
   */
  object TableMapping {
    private val id = "ID"
    private val firstName = "FIRST_NAME"
    private val lastName = "LAST_NAME"
    private val email = "EMAIL"
    private val phone = "PHONE"
    private val deleted = "DELETED"
    
    /** データベースの行をオブジェクトにデシリアライズする */
    def deserializer(from: Map[String, Any]): Contact = {
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

    /** データベースのテーブルをオブジェクトのリストにデシリアライズする */
    def listDeserializer(from: List[Map[String, Any]]): List[Contact] = from.map(deserializer(_))
  }
  
  object JsonMapping {
    private implicit val writes = new Writes[Contact] {
      def writes(contact: Contact) = Json.obj(
        "id" -> contact.id.value,
        "firstName" -> contact.name.firstName,
        "lastName" -> contact.name.lastName,
        "email" -> contact.email.value,
        "phone" -> contact.phone.value
      )
    }
    
    private val reads = (
      (__ \ "id").read[String] and
      (__ \ "firstName").read[String] and
      (__ \ "lastName").read[String] and
      (__ \ "email").read[String] and
      (__ \ "phone").read[String] and
      (__ \ "deleted").read[Boolean]
    )(Contact.apply _)
    
    /** オブジェクトをJsonにシリアライズする */
    def serializer(from: Contact) = Json.toJson(from)
    
    /** オブジェクトのリストをJsonにシリアライズする */
    def listSerializer(from: List[Contact]) = {
      // リスト要素を1つずつJsValueに変換し、JsValueのリストを作る
      val jsValueList = from.map(JsonMapping.serializer(_))
      
      // リストをJsValueに変換する
      Json.toJson(jsValueList) 
    }
  }
}