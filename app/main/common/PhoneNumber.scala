package main.common

import main.framework.Assert

class PhoneNumber(val value: String) {
  private val pattern = """(0\d{1,4}-|\(0\d{1,4}\) ?)?\d{1,4}-\d{4}"""
  
  // インスタンス化時に単項目チェックを実行する
  validate

  /** 単項目チェック */
  def validate {
    Assert.argumentMatch(value, pattern)
  }
}