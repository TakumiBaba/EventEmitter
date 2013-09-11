name := "EventEmitter"

organization := "com.takumibaba"

version := "0.1.0"

scalaVersion := "2.10.0"

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2" % "1.13" % "test"
)

initialCommands := "import com.takumibaba.eventemitter._"
