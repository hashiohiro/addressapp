package main.port.repository.common

class Insert(val columnList: List[Column], val table: Table, val values: List[Any]) {
  def get: String = {
    val insertColumns = columnList.map(_.name).mkString(",")
    val params = columnList.map(x => "?").mkString(", ")
    val valueList = values.mkString(",")
    val tableName = table.name
    
    s"INSERT INTO ${ tableName } (${ insertColumns }) VALUES (${ params })"
  }
}