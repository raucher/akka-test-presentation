ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "AkkaTestPresentation"
  )

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.8.4",
  "com.lihaoyi" %% "upickle" % "3.1.2",
  "com.typesafe.akka" %% "akka-testkit" % "2.8.4" % Test,
  "org.scalatest" %% "scalatest" % "3.2.17" % Test,
  "org.mockito" %% "mockito-scala" % "1.17.12" % Test,
)
