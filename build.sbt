enablePlugins(CommonSettingsPlugin)
enablePlugins(CoverallsWrapper)

name := "json4s-java-time"

organization := "com.meetup"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "org.json4s" %% "json4s-core" % "3.4.0" % "provided",
  "org.json4s" %% "json4s-native" % "3.4.0" % "test"
)

initialCommands := "import com.meetup._; import java.time._; import java.time.format._"

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

bintrayOrganization in ThisBuild := Some("meetup")

