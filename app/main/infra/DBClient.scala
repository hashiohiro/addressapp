package main.infra

import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet
import java.sql.PreparedStatement
import java.sql.SQLException

object DBClient {
  private val databaseDriver = "org.h2.Driver"
  private val url = "jdbc:h2:~/address"
  private val user = "sa"
  private val password = ""

  private lazy val connection: Connection = initializeConnection(url, user, password)
  
  
  /** DBClientのコネクションを初期化します */
  private def initializeConnection(url: String, user: String, password: String): Connection = {
    Class.forName(databaseDriver)
    val conn = DriverManager.getConnection(url, user, password)
    conn.setAutoCommit(false)
    conn.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE)

    conn
  }
  
  /** コミットします */
  private def commit = connection.commit
  
  /** ロールバックします */
  private def rollback = connection.rollback
  
  def createStatement(sql: String): PreparedStatement = connection.prepareStatement(sql)
  
  /** 問い合わせを実行します */
  def query(statement: PreparedStatement): Option[ResultSet] = {
    try {
      Some(statement.executeQuery)
    } catch {
      case e: SQLException => e.printStackTrace
      None
    }
  }
  
  /** 更新を実行します */
  def update(statement: PreparedStatement): Option[Int] = {
    try {
      Some(statement.executeUpdate)
    } catch { case e: SQLException =>
      this.rollback
      e.printStackTrace
      None
    }
  }
}