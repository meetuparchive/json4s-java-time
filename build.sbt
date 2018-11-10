name := "json4s-java-time"

organization := "com.meetup"

scalaVersion := "2.12.7"

crossScalaVersions := Seq("2.11.8", "2.12.7")

autoScalaLibrary := false

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-core" % "3.6.2" % "provided",
  "org.json4s" %% "json4s-native" % "3.6.2" % "test",
  "org.scalatest" %% "scalatest" % "3.0.5" % "test"
)

initialCommands := "import com.meetup._; import java.time._; import java.time.format._"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

bintrayOrganization in ThisBuild := Some("meetup")