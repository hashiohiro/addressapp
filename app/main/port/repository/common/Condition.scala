package main.port.repository.common

class Condition(val targetColumn: Column, val comparisonOperator: ComparisonOperator) {
  def get: String = s"${ targetColumn.columnName } ${ comparisonOperator.get } ?"
}