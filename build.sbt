import sbt._
import sbt.Keys._

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "prodman",
    libraryDependencies ++= Seq(
      filters,
      guice,
      jdbc,
      evolutions,
      "org.scalatestplus.play" %% "scalatestplus-play"           % "4.0.1" % Test,
    ) ++
      dbModule ++
      jsonModule ++
    Seq(
      "org.skinny-framework" %% "skinny-http-client" % "3.0.1",
      "log4j" % "log4j" % "1.2.17",
      "org.slf4j" % "slf4j-log4j12" % "1.7.26" % Test,

      // https://mvnrepository.com/artifact/org.postgresql/postgresql
      "org.postgresql" % "postgresql" % "42.2.8",
      // https://mvnrepository.com/artifact/mysql/mysql-connector-java
      "mysql" % "mysql-connector-java" % "8.0.18"
    )
  )

name := "prodman"

version := "0.1"

scalaVersion := "2.12.9"

val versions = Map(
  "scalikejdbc" -> "2.5.2",
  "circe" -> "0.12.1",
)

val jsonModule = Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
).map(_ % versions("circe")) :+
  ("com.dripower" %% "play-circe" % "2712.0")

val dbModule = Seq(
  "org.scalikejdbc"        %% "scalikejdbc",
  "org.scalikejdbc"        %% "scalikejdbc-config",
  "org.scalikejdbc"        %% "scalikejdbc-jsr310"
).map(_ % versions("scalikejdbc")) ++
  Seq(
  "org.scalikejdbc"        %% "scalikejdbc-play-initializer" % "2.7.1-scalikejdbc-3.3",
  "com.h2database"         %  "h2"                           % "1.4.192",
)