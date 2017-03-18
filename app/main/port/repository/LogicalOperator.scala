package main.port.repository

import main.common.ValueObject

/**
 * 論理演算子
 */
class LogicalOperator (val value: String) extends ValueObject {
  /**
   * 論理演算子を取得します。
   */
  def get = value
}