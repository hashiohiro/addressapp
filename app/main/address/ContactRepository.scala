package main.address

import java.sql.{ Connection, DriverManager }
import main.infra.{ DBClient, DBHelper }
import main.port.repository.{ Column, Repository, Table }
import main.common.{ AbstractId, EmailAddress, PersonName, PhoneNumber, ValueObject }
import main.address.ContactQueryMapper.TableMapping
import main.port.repository.{ Condition, LogicalOperator, ComparisonOperator }

object ContactRepository extends Repository {

  /** 当リポジトリが操作の対象とするテーブル */
  protected override val table = new Table("CONTACT")
  
  /** 操作対象テーブルが持つ列一覧 */
  protected override val columnList = List(
    new Column("ID"),
    new Column("FIRST_NAME"),
    new Column("LAST_NAME"),
    new Column("EMAIL"),
    new Column("PHONE"),
    new Column("DELETED")
  )
  
  

  /** 操作対象テーブルのプライマリキー定義 */
  protected override val primaryKey = columnList(0)
  
  /** 操作対象テーブルの削除フラグ定義 */
  protected override val deleteFlag = columnList(5)

  /** 連絡先をIDで検索します */
  def get(id: ContactId): Option[Contact] = super.getGenericData(id) match {
    case Some(m) => ContactQueryMapper.TableMapping.listDeserializer(m::Nil).headOption
    case None => None
  }
  
  /** 連絡先の一覧を検索します */
  def list: List[Contact] = {
    // 検索条件を構築する
    val conditions = List(new Condition(new Column("DELETED"), new ComparisonOperator("=")))
    val logicalOperators = List(new LogicalOperator(""))
    
    // 検索処理を実行する
    val result = super.listGenericData(conditions, logicalOperators)
    
    // 検索結果をContactオブジェクトにマッピングして返す
    ContactQueryMapper.TableMapping.listDeserializer(result)
  }

  /** 連絡先を登録します */
  def insert(contact: Contact): Int = {
    // PreparedStatementにバインドするパラメータ
    val params = List(
      contact.id.value,
      contact.name.firstName,
      contact.name.lastName,
      contact.email.value,
      contact.phone.value,
      contact.deleted
    )
    
    // インサート処理を実行する
    super.insertGenericData(params)
  }
  
  /** 連絡先を更新します */
  def update(contact: Contact): Int = {
    // 更新対象の条件を構築する
    val conditions = List(new Condition(primaryKey, new ComparisonOperator("=")))
    val logicalOperators = List(new LogicalOperator(""))
    
    // 更新対象の列
    val columnList = List(
      new Column("FIRST_NAME"),
      new Column("LAST_NAME"),
      new Column("EMAIL"),
      new Column("PHONE"),
      new Column("DELETED")
    )
    
    // PreparedStatementにバインドするパラメータ
    val params = List(
      contact.name.firstName,
      contact.name.lastName,
      contact.email.value,
      contact.phone.value,
      contact.deleted,
      contact.id.value
    )

    // 更新処理を実行する
    super.updateGenericData(columnList, params, conditions, logicalOperators)
  }

  /** 連絡先を論理削除します */
  def logicalDelete(id: ContactId): Int = {
    // 更新対象の条件を構築する
    val conditions = List(new Condition(primaryKey, new ComparisonOperator("=")))
    val logicalOperators = List(new LogicalOperator(""))
    
    // PreparedStatementにバインドするパラメータ
    val params = List(id.value)
    
    // 更新処理を実行する
    super.logicalDeleteGenericData(params, conditions, logicalOperators)
  }
}