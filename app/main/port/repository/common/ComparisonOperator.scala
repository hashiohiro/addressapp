package main.port.repository.common

import main.common.ValueObject

class ComparisonOperator (val value: String) extends ValueObject {
  def get = value
}