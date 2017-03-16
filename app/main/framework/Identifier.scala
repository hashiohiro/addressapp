package main.framework

object Identifier {
  def create: String = java.util.UUID.randomUUID.toString.replace("-", "").toUpperCase
}