package main.port.repository

import main.common.ValueObject

class Table(
  val name: String
) extends ValueObject {
  def get = name
}