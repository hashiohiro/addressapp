package main.infra

import com.fasterxml.jackson.core.`type`.TypeReference

object JsonObjectMapper {
  private val mapper = new com.fasterxml.jackson.databind.ObjectMapper
  private val scalaModule = com.fasterxml.jackson.module.scala.DefaultScalaModule
  private val typeRef = new TypeReference[Map[String, Any]] {}
  mapper.registerModule(scalaModule)
  
  /**
   * オブジェクトをJSONにシリアライズします。
   * @param value
   */
  def writeValueAsString(value:AnyRef):String = mapper.writeValueAsString(value)
  
  /**
   * JSONをMapにデシリアライズします。
   * @param json
   */
  def readValue(json: String): Map[String, Any] = mapper.readValue(json, typeRef)
}