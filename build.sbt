ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.18"

lazy val root = (project in file("."))
  .settings(
    name := "AkkaTestPresentation"
  )

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % "2.8.4",
  "com.typesafe.akka" %% "akka-testkit" % "2.8.4",
  "org.scalatest" %% "scalatest" % "3.2.17",
  "org.scalatestplus" %% "mockito-3-4" % "3.2.10.0"
)
