package main.port.repository.common

trait Table {
  val name: String
  
  val primaryKey: List[Column]

  def unapplySeq: Option[Seq[Column]]
  
  /** 削除フラグを取得します */
  def deleteFlag: Column
}