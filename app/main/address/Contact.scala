package main.address

import main.infra.Assert
import main.common.{ EmailAddress, PersonName, PhoneNumber, ValueObject }
import main.port.repository.h2.H2ContactRepository

/**
 * 連絡先
 * 
 * 当オブジェクトバリデーション・新規登録・更新・削除を提供します。
 */
class Contact(
  val id: ContactId,
  val name: PersonName,
  val email: EmailAddress,
  val phone: PhoneNumber,
  val deleted: Boolean
) extends ValueObject {
  
  // インスタンス化時に、当オブジェクト全体のバリデーションを実行する。
  validate

  /** 当オブジェクトのバリデーションを実行します。 */
  def validate {
    Assert.argumentNotEmpty(name.firstName)
    Assert.argumentNotEmpty(name.lastName)
    Assert.argumentNotEmpty(email.value)
    Assert.argumentNotEmpty(phone.value)
  }

  /** 新規登録処理 */
  def register {
    H2ContactRepository.add(this)
  }
  
  /** 更新処理 */
  def update {
  }
  
  /** 削除処理 */
  def delete {
    H2ContactRepository.remove(this)
  }
}

object Contact {
  private val id = "ID"
  private val firstName = "FIRST_NAME"
  private val lastName = "LAST_NAME"
  private val email = "EMAIL"
  private val phone = "PHONE"
  private val deleted = "DELETED"

  /**
   * ContactオブジェクトからMapを生成します。
   * 
   * @param contact Contact
   */
  def toMap(contact: Contact) = Map(
    "id" -> contact.id.value,
    "firstName" -> contact.name.firstName,
    "lastName" -> contact.name.lastName,
    "email" -> contact.email.value,
    "phone" -> contact.phone.value,
    "deleted" -> contact.deleted
  )
  
  /**
   * MapからContactオブジェクトを生成します。
   * 
   * @param from Map
   */
  def fromMap(from: Map[String, Any]) = new Contact(
    new ContactId(from(id).asInstanceOf[String]),
    new PersonName(
      from(firstName).asInstanceOf[String],
      from(lastName).asInstanceOf[String]  
    ),
    new EmailAddress(from(email).asInstanceOf[String]),
    new PhoneNumber(from(phone).asInstanceOf[String]),
    from(deleted).asInstanceOf[Boolean]
  )
}