package main.port.repository

class Condition(val targetColumn: Column, val comparisonOperator: ComparisonOperator) {
  def get: String = s"${ targetColumn.get } ${ comparisonOperator.get } ?"
}