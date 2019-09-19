import sbt._
import sbt.Keys._

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := "prodman",
    libraryDependencies ++=Seq(
      filters,
      guice,
      jdbc,
      evolutions,
      "org.scalikejdbc"        %% "scalikejdbc"                  % scalikejdbcVersion,
      "org.scalikejdbc"        %% "scalikejdbc-config"           % scalikejdbcVersion,
      "org.scalikejdbc"        %% "scalikejdbc-jsr310"           % scalikejdbcVersion,
      "org.scalikejdbc"        %% "scalikejdbc-play-initializer" % "2.7.1-scalikejdbc-3.3",
      "org.scalatestplus.play" %% "scalatestplus-play"           % "4.0.1" % Test,
      "com.h2database"         %  "h2"                           % "1.4.192",
    )
  )

name := "prodman"

version := "0.1"

scalaVersion := "2.12.9"

val scalikejdbcVersion = "2.5.2"