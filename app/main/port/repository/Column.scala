package main.port.repository

/**
 * DBテーブルの列を表現します。
 */
class Column(val columnName: String) {
  /**
   * 列名を取得します。
   */
  def get = columnName
}