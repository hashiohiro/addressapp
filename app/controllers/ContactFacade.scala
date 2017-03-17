package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import main.address.ContactRepository
import main.address.ContactId
import main.address.ContactQueryMapper
import play.api.libs.json.Json
import main.common.PhoneNumber
import main.common.EmailAddress
import main.common.PersonName
import main.address.Contact

@Singleton
class ContactFacade @Inject() extends Controller {

  def index = Action {
    Ok("index")
  }
  
  /** 連絡先を1件取得します */
  def get(id: String) = Action {
    val contact = ContactRepository.get(new ContactId(id))

    // DBから取得した連絡先をJSONにシリアライズして返す
    contact.map(c => ContactQueryMapper.JsonMapping.listSerializer(c :: Nil)) match {
      case Some(res)  => Ok(res)
      case None => NotFound
    }
  }
  
  /** 連絡先を一覧で取得します */
  def list = Action {
    val contacts = ContactRepository.list

    Ok(ContactQueryMapper.JsonMapping.listSerializer(contacts))
  }
  
  /** 新しい連絡先を登録します */
  def register(id: String, firstName: String, lastName: String, email: String, phone: String, deleted: Boolean) = Action {
    // TODO: 連絡先の新規登録処理
    Ok("register contact")
  }
}