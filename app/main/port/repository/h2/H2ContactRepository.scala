package main.port.repository.h2

import main.port.repository.Repository
import main.port.repository.common.{ Condition, ComparisonOperator, LogicalOperator }
import main.address.Contact
import main.address.ContactId
import main.address.ContactTable
import main.address.ContactRepository

object H2ContactRepository extends Repository with ContactRepository {

  /** 当リポジトリが操作の対象とするテーブル */
  protected override val table = new ContactTable

  /** 連絡先をIDで検索します */
  override def get(id: ContactId): Option[Contact] = super.getGenericData(id) match {
    case Some(m) => Some(Contact.fromMap(m))
    case None => None
  }
  
  /** 連絡先の一覧を検索します */
  override def list: List[Contact] = {
    // 検索条件を構築する
    val conditions = List(new Condition(this.table.deleteFlag, new ComparisonOperator("=")))
    val logicalOperators = List(new LogicalOperator(""))
    
    // 検索処理を実行する
    val result = super.listGenericData(conditions, logicalOperators)
    
    // 検索結果をContactオブジェクトにマッピングして返す
    result.map(Contact.fromMap(_))
  }

  /** 連絡先を登録します */
  override def add(contact: Contact): Int = {
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
  
  /** 連絡先を論理削除します */
  override def remove(contact: Contact): Int = {
    // 更新対象の条件を構築する
    val conditions = super.primaryKeyCondition
    val logicalOperators = List(new LogicalOperator(""))
    
    // PreparedStatementにバインドするパラメータ
    val params = List(contact.id.value)
    
    // 更新処理を実行する
    super.logicalDeleteGenericData(params, conditions, logicalOperators)
  }
}