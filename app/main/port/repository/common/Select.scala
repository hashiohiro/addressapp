package main.port.repository.common

import main.common.ValueObject

class Select(val columnList: List[Column], val table: Table) extends ValueObject {
  
  /**
   * SELECT文を取得します。
   */
  def get: String = {
    val selectColumns = columnList.map(_.columnName).mkString(",")
    val tableName = table.name
    
    s"SELECT ${ selectColumns } FROM ${ tableName }"
  }
  
  /**
   * SELECT文を条件付きで取得します。
   * 
   * @param conditions 検索条件リスト
   * @param logicalOperators 論理演算子リスト
   */
  def withCondition(conditions: List[Condition], logicalOperators: List[LogicalOperator]): String = {
    val conditionsWithLogicalOperator = conditions.zip(logicalOperators).map(x => {
      val (cnd, crit) = x
      s"${ cnd.get } ${ crit.get }"
    })
    s"${ this.get } WHERE ${ conditionsWithLogicalOperator.mkString(" ") }"
  }
}