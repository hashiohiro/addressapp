package main.infra

import java.sql.PreparedStatement
import java.sql.ResultSet
import scala.collection.mutable.ListBuffer
import java.sql.Date
import java.time.LocalDate
import java.sql.Timestamp
import java.time.LocalDateTime

object DBHelper {
  /**
   * PreparedStatementにパラメータをバインドします。
   * @param params パラメータ
   * @param statement PreparedStatement
   */
  def bindParams(params: List[Any], statement: PreparedStatement) = {
    val indexes = 1 to params.length
    val paramsWithIndex = params zip indexes
    paramsWithIndex.foreach {
      case (str: String, index: Int) =>  statement.setString(index, str)
      case (int: Int, index: Int) => statement.setInt(index, int)
      case (double: Double, index: Int) => statement.setDouble(index, double)
      case (bool: Boolean, index: Int) => statement.setBoolean(index, bool)
      case (date: LocalDate, index: Int) => statement.setDate(index, Date.valueOf(date))
      case (timestamp: LocalDateTime, index: Int) => statement.setTimestamp(index, Timestamp.valueOf(timestamp))
      case (typeValue:Any, index: Int) => throw new PreparedStatementBindParamsException(s"Type: ${ typeValue } Index: ${ index }")
    }
  }
  
  /**
   * ResultSetオブジェクトを走査して表形式のデータ構造を返します。
   * @param result ResultSet
   */
  def parseQueryResult(result: ResultSet): List[Map[String, Any]] = {
    val buffer = ListBuffer.empty[Map[String, Any]]
    val columnNum = result.getMetaData.getColumnCount
    while (result.next) {
      val indexList = (1 to columnNum).toList
      
      // ResultSetから1行分の列名をまとめたListを作る
      val classNameList = indexList.map(result.getMetaData.getColumnClassName(_))
      
      // ResultSetから1行分の列名をまとめたListを作る
      val columnNameList = indexList.map(result.getMetaData.getColumnName(_))

      // ResultSetから1行分の列名と列値のMapを作る
      val columnMap = scala.collection.mutable.Map.empty[String, Any]
      classNameList.zip(columnNameList).foreach{
        case (className, name) if (className == "java.lang.String") => columnMap += name -> result.getString(name)
        case (className, name) if (className == "java.lang.Integer") => columnMap += name -> result.getInt(name)
        case (className, name) if (className == "java.lang.Double") => columnMap += name -> result.getDouble(name)
        case (className, name) if (className == "java.lang.Boolean") => columnMap += name -> result.getBoolean(name)
        case (className, name) if (className == "java.sql.Date") => columnMap += name -> result.getDate(name)
        case (className, name) if (className == "java.sql.Timestamp") => columnMap += name -> result.getTimestamp(name)
        case (className, name) => throw new QueryResultParseException(s"ClassName: ${ className } ColumnName: ${ name }")
      }
      buffer += columnMap.toMap
    }
    buffer.toList
  }
}

class PreparedStatementBindParamsException(message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)

/** ResultSetがパースできなかった場合に発生する例外です */
class QueryResultParseException(message: String = null, cause: Throwable = null) extends RuntimeException(message, cause)