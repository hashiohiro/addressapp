package main.port.repository.h2

import main.address.{ Contact, ContactId, ContactRepository }
import main.infra.DBHelper
import main.port.repository.h2.infra.DBClient

class H2ContactRepository extends ContactRepository {

  override def contactOfId(id: ContactId): Option[Contact] = {
    val statement = DBClient.createStatement("SELECT * FROM CONTACT WHERE ID = ? AND DELETED = ?")
    val params = id.value :: false :: Nil
    DBHelper.bindParams(params, statement)
    
    DBHelper.parseQueryResult(statement.executeQuery).headOption.map(Contact.fromMap(_))
  }

  /** 連絡先の一覧を検索します */
  override def list: List[Contact] = {
    val statement = DBClient.createStatement("SELECT * FROM CONTACT WHERE DELETED = ?")
    val params = false :: Nil
    DBHelper.bindParams(params, statement)
    
    DBHelper.parseQueryResult(statement.executeQuery).map(Contact.fromMap(_))
  }

  /** 連絡先を登録します */
  override def add(contact: Contact): Boolean = {
    val sql = "INSERT INTO CONTACT (ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, DELETED) VALUES (?, ?, ?, ?, ?, ?)"
    val statement = DBClient.createStatement(sql)
    val params = List(
      contact.id.value,
      contact.name.firstName,
      contact.name.lastName,
      contact.email.value,
      contact.phone.value,
      contact.deleted
    )
    DBHelper.bindParams(params, statement)
    
    statement.executeUpdate > 0
  }

  /** 連絡先を論理削除します */
  override def remove(contact: Contact): Boolean = {
    val sql = "UPDATE CONTACT SET DELETED = ? WHERE ID = ?"
    val statement = DBClient.createStatement(sql)
    val params = false :: contact.id.value :: Nil
    DBHelper.bindParams(params, statement)
    
    statement.executeUpdate > 0
  }
}