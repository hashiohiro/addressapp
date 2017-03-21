package main.infra

object JsonObjectMapper {
  private val mapper = new com.fasterxml.jackson.databind.ObjectMapper
  private val scalaModule = com.fasterxml.jackson.module.scala.DefaultScalaModule
  mapper.registerModule(scalaModule)
  
  /**
   * オブジェクトをJSONにシリアライズします。
   * @param value
   */
  def writeValueAsString(value:AnyRef):String = mapper.writeValueAsString(value)
}