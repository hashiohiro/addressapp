package main.address

import main.common.PersonName
import main.common.EmailAddress
import main.common.PhoneNumber
import main.infra.Assert

class Contact(
  val id: ContactId,
  val name: PersonName,
  val email: EmailAddress,
  val phone: PhoneNumber,
  val deleted: Boolean
) {
  
  // インスタンス化時に単項目チェックを実行する
  validate

  /** 単項目チェック */
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