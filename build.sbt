name := "scala-json-benchmark"

version := "1.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-jackson" % "3.2.11",
  "io.spray" %%  "spray-json" % "1.3.1",
  "net.liftweb" %% "lift-json" % "3.0-M5",
  "com.propensive" %% "rapture-json-jackson" % "1.1.0",
  "com.typesafe.play" %% "play-json" % "2.3.4"
)

