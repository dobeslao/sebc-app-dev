name := "SparkFlumeHBase"
version := "1.1"
scalaVersion := "2.11.8"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

// ADD CLOUDERA REPO AND DEPENDENCIES FOR SPARK APPS
resolvers +=  "cloudera-repos" at "http://repository.cloudera.com/artifactory/cloudera-repos/"

libraryDependencies ++= Seq(
    "org.apache.spark" %% "spark-core" % "2.3.0.cloudera2" % "provided",
    "org.apache.spark" %% "spark-sql" % "2.3.0.cloudera2" % "provided",
    "org.apache.spark" %% "spark-streaming-flume" % "2.3.0.cloudera2" % "provided",
    "org.apache.spark" %% "spark-streaming" % "2.3.0.cloudera2" % "provided",
    "org.apache.spark" %% "spark-mllib" % "2.3.0.cloudera2" % "provided",
    "org.apache.hbase" % "hbase-client" % "1.2.0-cdh5.14.2" % "provided",
    "org.apache.hbase" % "hbase-server" % "1.2.0-cdh5.14.2" % "provided",
    "org.apache.hbase" % "hbase-common" % "1.2.0-cdh5.14.2" % "provided",
    "org.apache.hbase" % "hbase-spark" % "1.2.0-cdh5.14.2" % "provided",
    "org.scala-lang" % "scala-reflect" % scalaVersion.value
)

