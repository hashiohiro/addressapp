package main.address

import java.sql.DriverManager
import java.sql.Connection
import main.common.PersonName
import main.common.EmailAddress
import main.common.PhoneNumber
import main.infra.DBClient
import main.infra.DBHelper
import main.port.repository.Repository

object ContactRepository extends Repository {
  /** 連絡先をIDで検索します */
  def get(id: ContactId): Option[Contact] = {
    val sql = "SELECT * FROM CONTACT WHERE ID = ? AND DELETED = false"
    val statement = DBClient.createStatement(sql)

    DBHelper.bindParams(List(id.value), statement)

    val queryResult = DBClient.query(statement).map(DBHelper.parseQueryResult(_))

    queryResult match {
      case Some(list) => ContactQueryMapper.mapping(list).headOption
      case None => None
    }
  }
  
  /** 連絡先の一覧を検索します */
  def list: List[Contact] = {
    val sql = "SELECT * FROM CONTACT WHERE DELETED = false"
    val statement = DBClient.createStatement(sql)
    
    val queryResult = DBClient.query(statement).map(DBHelper.parseQueryResult(_))
    
    queryResult match {
      case Some(list) => ContactQueryMapper.mapping(list)
      case None => List()
    }
  }

  /** 連絡先を登録します */
  def insert(contact: Contact): Int = {
    val sql = """
                INSERT INTO CONTACT (ID, FIRST_NAME, LAST_NAME, EMAIL, PHONE, DELETED)
                VALUES (?, ?, ?, ?, ?, ?);
              """
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

    val affectedRowCount = DBClient.update(statement).getOrElse(0)
    
    affectedRowCount
  }
  
  /** 連絡先を更新します */
  def update(contact: Contact): Int = {
    val sql = """
                UPDATE CONTACT SET
                  FIRST_NAME = ?,
                  LAST_NAME = ?,
                  EMAIL = ?,
                  PHONE = ? 
                WHERE
                  ID = ? AND
                  DELETED = false
              """
    val statement = DBClient.createStatement(sql)

    val params = List(
      contact.name.firstName,
      contact.name.lastName,
      contact.email.value,
      contact.phone.value,
      contact.id.value
    )

    DBHelper.bindParams(params, statement)

    val affectedRowCount = DBClient.update(statement).getOrElse(0)
    
    affectedRowCount
  }

  /** 連絡先を論理削除します */
  def logicalDelete(id: ContactId): Int = {
    val sql = """
                UPDATE CONTACT SET
                  DELETED = true
                WHERE
                  ID = ? AND
                  DELETED = false
              """
    val statement = DBClient.createStatement(sql)

    DBHelper.bindParams(List(id.value), statement)

    val affectedRowCount = DBClient.update(statement).getOrElse(0)

    affectedRowCount
  }
}