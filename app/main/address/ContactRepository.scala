package main.address

trait ContactRepository {
  /** 連絡先を1件取得します */
  def contactOfId(id: ContactId): Option[Contact]  

  /** 連絡先の一覧を検索します */
  def list: List[Contact]

  /** 連絡先を登録します */
  def add(contact: Contact): Boolean

  /** 連絡先を論理削除します */
  def remove(contact: Contact): Boolean
}