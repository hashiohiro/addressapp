package controllers

import javax.inject._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import main.infra.JsonObjectMapper
import main.common.{ EmailAddress, PersonName, PhoneNumber }
import main.address.{ Contact, ContactId }
import main.port.repository.h2_orig.H2ContactRepository

@Singleton
class ContactFacade @Inject() extends Controller {

  def index = Action {
    Ok("index")
  }
  
  /** 連絡先を1件取得します */
  def get(id: String) = Action {
    // JSONへシリアライズするためのMapを生成する
    val contact = H2ContactRepository.contactOfId(new ContactId(id))
    val contactMap = contact.map(Contact.toMap(_))
    
    // JSONへシリアライズする
    contactMap.map(JsonObjectMapper.writeValueAsString(_)) match {
      case Some(res) => Ok(res)
      case None => NotFound
    }
  }
  
  /** 連絡先を一覧で取得します */
  def list = Action {
    // JSONへシリアライズするためのMapを生成する
    val contacts = H2ContactRepository.list
    val contactsMap = contacts.map(Contact.toMap(_))

    // JSONへシリアライズする
    Ok(JsonObjectMapper.writeValueAsString(contactsMap))
  }
  
  /** 新しい連絡先を登録します */
  def register(id: String, firstName: String, lastName: String, email: String, phone: String, deleted: Boolean) = Action {
    // TODO: 連絡先の新規登録処理
    Ok("register contact")
  }
}