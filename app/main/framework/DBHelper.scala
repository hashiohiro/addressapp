package main.framework

import java.sql.PreparedStatement
import java.sql.ResultSet
import scala.collection.mutable.ListBuffer

object DBHelper {
  def bindParams(params: List[Any], statement: PreparedStatement) = {
    val indexes = 1 to params.length
    val paramsWithIndex = params zip indexes
    paramsWithIndex.foreach {
      case (str: String, index: Int) =>  statement.setString(index, str)
      case (bool: Boolean, index: Int) => statement.setBoolean(index, bool)
      case (typeValue:Any, index: Int) => throw new PreparedStatementBindParamsException(s"Type: ${ typeValue } Index: ${ index }")
    }
  }
  
  /** ResultSetをパースしてMapのList形式で値を返す */
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