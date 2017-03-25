name := """Address"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test,
  "com.h2database"  %  "h2"                           % "1.4.193",
  "com.fasterxml.jackson.module" % "jackson-module-scala_2.11" % "2.7.2",
  "org.mongodb.scala" %% "mongo-scala-driver" % "1.1.+"
)

EclipseKeys.preTasks := Seq(compile in Compile)
