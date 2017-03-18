package main.address

import main.infra.Assert
import main.common.AbstractId

class ContactId(override val value: String) extends AbstractId {
  private val max: Int = 33
  private val min: Int = 1
  private val pattern: String = "[0-9A-F]+"

  // インスタンス化時に単項目チェックを実行する
  validate
  
  /** 単項目チェック */
  override def validate {
    Assert.argumentNotEmpty(value)
    Assert.argumentLength(value, max, min)
    Assert.argumentMatch(value, pattern)
  }
}