package main.common

/**
 * エンティティーの識別子
 */
trait AbstractId extends ValueObject {
  val value: String
  
  /** 識別子を取得する */
  def get = value

  /** バリデーション */
  def validate: Unit
}