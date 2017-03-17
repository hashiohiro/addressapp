package main.common

import main.infra.Assert

class EmailAddress(val value: String) {
  private val pattern = """^(?:(?:(?:(?:[a-zA-Z0-9_!#\$\%&'*+/=?\^`{}~|\-]+)(?:\.(?:[a-zA-Z0-9_!#\$\%&'*+/=?\^`{}~|\-]+))*)|(?:"(?:\\[^\r\n]|[^\\"])*")))\@(?:(?:(?:(?:[a-zA-Z0-9_!#\$\%&'*+/=?\^`{}~|\-]+)(?:\.(?:[a-zA-Z0-9_!#\$\%&'*+/=?\^`{}~|\-]+))*)|(?:\[(?:\\\S|[\x21-\x5a\x5e-\x7e])*\])))$"""
  
  // インスタンス化時に単項目チェックを実行する
  validate
  
  /** 単項目チェック */
  def validate {
    Assert.argumentMatch(value, pattern)
  }
}