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
  "org.scalikejdbc" %% "scalikejdbc"                  % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-config"           % "2.5.1",
  "org.scalikejdbc" %% "scalikejdbc-play-initializer" % "2.5.1"
)

EclipseKeys.preTasks := Seq(compile in Compile)
