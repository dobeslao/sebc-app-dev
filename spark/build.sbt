name := "SparkBatchExample"
version := "1.1"
scalaVersion := "2.10.5"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

// ADD CLOUDERA REPO AND DEPENDENCIES FOR SPARK APPS
resolvers += "Cloudera Repository" at "https://repository.cloudera.com/artifactory/cloudera-repos/"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "1.6.0-cdh5.14.3-SNAPSHOT" % "provided",
  "org.apache.spark" %% "spark-sql" % "1.6.0-cdh5.14.3-SNAPSHOT" % "provided",
  "org.apache.spark" %% "spark-streaming-flume" % "1.6.0-cdh5.14.3-SNAPSHOT" % "provided",
  "org.apache.spark" %% "spark-streaming" % "1.6.0-cdh5.14.3-SNAPSHOT" % "provided",
  "org.scala-lang" % "scala-reflect" % scalaVersion.value
)
