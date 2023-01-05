ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

lazy val root = (project in file("."))
  .settings(
    name := "ProyectoI",
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.9.3",
    libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.10"
  )
