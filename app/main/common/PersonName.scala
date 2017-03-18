package main.common

import main.infra.Assert

/**
 * 人名
 */
class PersonName(val firstName: String, val lastName: String) extends ValueObject {
  // インスタンス化時にバリデーションを実行する
  validate

  /** バリデーション */
  def validate {
  }
}
  