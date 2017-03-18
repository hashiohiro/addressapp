package main.port.repository

class Update(table: Table, columnList: List[Column]) {
  def get: String = {
    val tableName = table.get
    
    val updateColumns = columnList.map(column => column.get + " = ?")
    s"UPDATE ${ tableName } SET ${ updateColumns.mkString(", ") }"
  }
  
  def withCondition(conditions: List[Condition], logicalOperators: List[LogicalOperator]): String = {
    val conditionsWithLogicalOperator = conditions.zip(logicalOperators).map(x => {
      val (cnd, crit) = x
      s"${ cnd.get } ${ crit.get }"
    })
    s"${ this.get } WHERE ${ conditionsWithLogicalOperator.mkString(" ") }"
  }
}