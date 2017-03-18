package main.port.repository

import main.common.ValueObject

class ComparisonOperator (val value: String) extends ValueObject {
  def get = value
}