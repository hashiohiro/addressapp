package main.address

import main.infra.Assert
import main.common.{ EmailAddress, PersonName, PhoneNumber, ValueObject }

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

  /** 当オブジェクト全体のバリデーションを実行します。 */
  def validate {
    Assert.argumentNotEmpty(name.firstName)
    Assert.argumentNotEmpty(name.lastName)
    Assert.argumentNotEmpty(email.value)
    Assert.argumentNotEmpty(phone.value)
    Assert.argumentNotEmpty(name.lastName)
  }

  /** 新規登録処理 */
  def register {
    ContactRepository.insert(this)
  }
  
  /** 更新処理 */
  def update {
    ContactRepository.update(this)
  }
  
  /** 削除処理 */
  def delete {
    ContactRepository.logicalDelete(this.id)
  }
}

object Contact {
  def apply(id: String, firstName: String, lastName: String, email: String, phone: String, deleted: Boolean): Contact =
    new Contact(new ContactId(id), new PersonName(firstName, lastName), new EmailAddress(email), new PhoneNumber(phone), deleted)

  def unapply(contact: Contact) = Option((contact.id, contact.name.firstName, contact.name.lastName, contact.email.value, contact.phone.value, contact.deleted))
}