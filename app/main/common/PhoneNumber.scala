package main.common

import main.infra.Assert

/**
 * 電話番号
 */
class PhoneNumber(val value: String) extends ValueObject {
  private val pattern = """(0\d{1,4}-|\(0\d{1,4}\) ?)?\d{1,4}-\d{4}"""
  
  // インスタンス化時にバリデーションを実行する
  validate

  /** バリデーション */
  def validate {
    Assert.argumentMatch(value, pattern)
  }
}