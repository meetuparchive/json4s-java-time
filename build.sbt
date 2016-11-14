enablePlugins(CommonSettingsPlugin)

name := "json4s-java-time"

organization := "com.meetup"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-core" % "3.4.0" % "provided",
  "org.json4s" %% "json4s-native" % "3.4.0" % "test"
)

initialCommands := "import com.meetup._; import java.time._; import java.time.format._"
