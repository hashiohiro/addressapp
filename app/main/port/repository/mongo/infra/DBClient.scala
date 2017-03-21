package main.port.repository.mongo.infra

import org.mongodb.scala.MongoClient

object DBClient {
  /* 使用するデータベースを指定する */
  private val dbName = "Address"

  private lazy val client = MongoClient()
  private lazy val database = client.getDatabase(dbName)
}