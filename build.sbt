name := "VendingMachine"

version := "1.0"

scalaVersion := "2.11.8"

organization := "com.mlysiu"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"
  Seq(
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-routing" % sprayV,
    "io.spray" %% "spray-json" % "1.3.2",
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "org.scalaz" %% "scalaz-core" % "7.2.5",
    "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
    "org.scalatest" %% "scalatest" % "3.0.0" % "test",
    "io.spray" %% "spray-testkit" % sprayV % "test"
  )
}