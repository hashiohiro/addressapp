package main.port.repository

/**
 * 論理削除用のSQL
 */
class LogicalDelete(table: Table, deleteFlag: Column) {
  /**
   * 論理削除用のSQLを取得します。
   */
  def get: String = {
    val tableName = table.get
    
    val updateColumns = s"${ deleteFlag.get } = true"
    s"UPDATE ${ tableName } SET ${ updateColumns }"
  }
  /**
   * 論理削除用のSQLを条件付きで取得します。
   */
  def withCondition(conditions: List[Condition], logicalOperators: List[LogicalOperator]): String = {
    val conditionsWithLogicalOperator = conditions.zip(logicalOperators).map(x => {
      val (cnd, crit) = x
      s"${ cnd.get } ${ crit.get }"
    })
    s"${ this.get } WHERE ${ conditionsWithLogicalOperator.mkString(" ") }"
  }
}