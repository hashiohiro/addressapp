package main.port.repository

import main.infra.{ DBHelper }
import main.port.repository.h2.infra.DBClient
import main.common.{ AbstractId, ValueObject }
import main.address.{ Contact, ContactId }
import main.port.repository.common.LogicalDelete
import main.port.repository.common.Column
import main.port.repository.common.Condition
import main.port.repository.common.ComparisonOperator
import main.port.repository.common.LogicalOperator
import main.port.repository.common.Insert
import main.port.repository.common.Update
import main.port.repository.common.Table
import main.port.repository.common.Select

trait Repository {
  protected val table: Table

  /**
   * 主キーを条件としたSELECTを実行します。
   * 
   * @param id id
   */
  protected def getGenericData(id: AbstractId): Option[Map[String, Any]] = {
    // 検索条件リストを作る

    val conditions = this.primaryKeyCondition ::: List(new Condition(this.table.deleteFlag, new ComparisonOperator("="))) 
    
    // 検索条件リストを演算子で結合する
    val logicalOperators = List(new LogicalOperator("AND"), new LogicalOperator(""))
    
    // PreparedStatementを作成し、パラメータをバインドする
    val sql = new Select(this.table.unapplySeq.get.toList, table).withCondition(conditions, logicalOperators)
    val statement = DBClient.createStatement(sql)
    DBHelper.bindParams(List(id.value, false), statement)
    
    // オプションでラップした値を返す
    DBClient.query(statement).map(DBHelper.parseQueryResult(_)) match {
      case Some(list) => list.headOption
      case None => None
    }
  }
  
  /**
   * SELECTを実行します。
   * 
   * @param condition 検索条件List
   * @param logicalOperators 条件演算子List
   */
  // TODO: 自由度の高い検索ができるようにしよう。
  protected def listGenericData(conditions: List[Condition], logicalOperators: List[LogicalOperator]): List[Map[String, Any]] = {
    // SQLを生成する
    val sql = new Select(this.table.unapplySeq.get.toList, table).withCondition(conditions, logicalOperators)
    
    // PreparedStatementにパラメータを設定する
    val statement = DBClient.createStatement(sql)
    DBHelper.bindParams(List(false), statement)
    
    //　検索結果が返ってきた場合は結果をListで返す。返ってこなかった場合は空のListを返す。
    DBClient.query(statement).map(DBHelper.parseQueryResult(_)) match {
      case Some(list) => list
      case None => List()
    }
  }
  
  /**
   * 列指定なしでINSERTを実行します。
   * 
   * INSERT INTO `tableName` (columnList) VALUES(valueList)
   * @param valueList INSERTする値リスト
   */
  protected def insertGenericData(valueList: List[Any]): Int = {
    // SQLを生成する
    val sql = new Insert(this.table.unapplySeq.get.toList, this.table, valueList).get
    
    // PreparedStatementにパラメータを設定する
    val statement = DBClient.createStatement(sql)
    DBHelper.bindParams(valueList, statement)
    
    // 更新処理実行後に件数を取得する
    DBClient.update(statement) match {
      case Some(cnt) => {
        DBClient.commit
        cnt
      }
      case None => 0
    }
  }
  
  /**
   * 列指定ありでINSERTを実行します。
   * 
   * INSERT INTO `tableName` (columnList) VALUES(valueList)
   * @param columnList INSERT先の列リスト
   * @param valueList INSERTする値リスト
   */
  protected def insertGenericData(columnList: List[Column], valueList: List[Any]): Int = {
    // SQLを生成する
    val sql = new Insert(columnList, this.table, valueList).get
    
    // PreparedStatementにパラメータを設定する
    val statement = DBClient.createStatement(sql)
    DBHelper.bindParams(valueList, statement)
    
    // 更新処理実行後に件数を取得する
    DBClient.update(statement) match {
      case Some(cnt) => {
        DBClient.commit
        cnt
      }
      case None => 0
    }
  }
  
  /**
   * UPDATEを実行します。
   * 
   * UPDATE `tableName` SET updateColumnListItem1 = ?, updateColumnListItem2 = ? WHERE conditions1(ID = ?) logicalOperators1(AND) conditions2(DELETED = ?) logicalOperators2("")
   * @param updateColumnList 更新する列
   * @param valueList 更新値
   * @param conditions 検索条件
   * @param logicalOperators 
   */
  protected def updateGenericData(updateColumnList: List[Column], valueList: List[Any], conditions: List[Condition], logicalOperators: List[LogicalOperator]): Int = {
    // SQLを生成する
    val sql = new Update(this.table, updateColumnList).withCondition(conditions, logicalOperators)
    
    // PreparedStatementにパラメータをを設定する
    val statement = DBClient.createStatement(sql)
    DBHelper.bindParams(valueList, statement)
    
    // 更新処理実行後に件数を取得する
    DBClient.update(statement) match {
      case Some(cnt) => {
        DBClient.commit
        cnt
      }
      case None => 0
    }
  }
  
  /**
   * 論理削除を実行します。
   * 
   * @param valueList 値リスト
   * @param conditions 検索条件リスト
   * @param logicalOperators 論理演算子リスト
   * 
   */
  protected def logicalDeleteGenericData(valueList: List[Any], conditions: List[Condition], logicalOperators: List[LogicalOperator]): Int = {
    // SQLを生成する
    val sql = new LogicalDelete(this.table, this.table.deleteFlag).withCondition(conditions, logicalOperators)
    
    // PreparedStatementにパラメータをを設定する
    val statement = DBClient.createStatement(sql)
    DBHelper.bindParams(valueList, statement)
    
    // 更新処理実行後に件数を取得する
    DBClient.update(statement) match {
      case Some(cnt) => {
        DBClient.commit
        cnt
      }
      case None => 0
    }
  }
  
  /**
   * 主キー条件を取得します。
   */
  protected def primaryKeyCondition: List[Condition] = this.table.primaryKey.map(key => new Condition(key, new ComparisonOperator("=")))
}