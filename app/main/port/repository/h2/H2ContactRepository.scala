package main.port.repository.h2

import main.address.{ Contact, ContactId, ContactRepository }

class H2ContactRepository extends ContactRepository {
  override def contactOfId(id: ContactId): Option[Contact] = {
    None
  }

  /** 連絡先の一覧を検索します */
  override def list: List[Contact] = {
    List.empty[Contact]
  }

  /** 連絡先を登録します */
  override def add(contact: Contact): Int = {
    1
  }

  /** 連絡先を論理削除します */
  override def remove(contact: Contact): Int = {
    1
  }
}