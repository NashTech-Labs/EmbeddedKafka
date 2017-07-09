name := "EmbeddedKafka"

version := "1.0"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.0.1" % "test",
  //kafka
  "org.apache.kafka" % "kafka-clients" % "0.10.2.0",
  //embeddedkafka
  "net.manub" %% "scalatest-embedded-kafka" % "0.13.1" % "test"
).map(_.exclude("org.slf4j", "log4j-over-slf4j"))