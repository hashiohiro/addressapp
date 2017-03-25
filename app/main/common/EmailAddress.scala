package main.common

import main.infra.Assert

/**
 * メールアドレス
 */
class EmailAddress(val value: String) extends ValueObject {
  private val pattern = """^(?:(?:(?:(?:[a-zA-Z0-9_!#\$\%&'*+/=?\^`{}~|\-]+)(?:\.(?:[a-zA-Z0-9_!#\$\%&'*+/=?\^`{}~|\-]+))*)|(?:"(?:\\[^\r\n]|[^\\"])*")))\@(?:(?:(?:(?:[a-zA-Z0-9_!#\$\%&'*+/=?\^`{}~|\-]+)(?:\.(?:[a-zA-Z0-9_!#\$\%&'*+/=?\^`{}~|\-]+))*)|(?:\[(?:\\\S|[\x21-\x5a\x5e-\x7e])*\])))$"""
  
  // インスタンス化時にバリデーションを実行する
  validate
  
  /** 単項目チェック */
  def validate {
    Assert.argumentMatch(value, pattern)
  }
}