package main.infra

object Identifier {
  def create: String = java.util.UUID.randomUUID.toString.replace("-", "").toUpperCase
}