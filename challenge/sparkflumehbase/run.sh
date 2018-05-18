#!/bin/sh

export SPARK_DIST_CLASSPATH=$(hadoop classpath):$(hbase classpath)

HBASE_LIB="/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hbase"
HTRACE_LIB="/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/jars"
HIVE_LIB="/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hive/lib"

SPARK_CLASS=com.cloudera.sdk.SparkStreamPollingExample
SPARK_BUILD_DIR=.
SPARK_JAR=sparkflumehbase_2.11-1.1.jar
# HOST=0.0.0.0
#HOST=127.0.0.1
HOST=$(hostname -f)
PORT=41415

DEPLOY_MODE=client
MASTER=yarn

EXTERNAL_JARS="--conf \"spark.driver.extraClassPath=${HBASE_LIB}/hbase-common-1.2.0-cdh5.14.2.jar:${HBASE_LIB}/hbase-client-1.2.0-cdh5.14.2.jar:${HBASE_LIB}/hbase-server-1.2.0-cdh5.14.2.jar:${HBASE_LIB}/hbase-spark-1.2.0-cdh5.14.2.jar:${HBASE_LIB}/hbase-protocol-1.2.0-cdh5.14.2.jar:${HTRACE_LIB}/htrace-core-3.2.0-incubating.jar:${HIVE_LIB}/*.jar\" --conf \"spark.executor.extraClassPath=${HBASE_LIB}/hbase-common-1.2.0-cdh5.14.2.jar:${HBASE_LIB}/hbase-client-1.2.0-cdh5.14.2.jar:${HBASE_LIB}/hbase-server-1.2.0-cdh5.14.2.jar:${HBASE_LIB}/hbase-spark-1.2.0-cdh5.14.2.jar:${HBASE_LIB}/hbase-protocol-1.2.0-cdh5.14.2.jar:${HTRACE_LIB}/htrace-core-3.2.0-incubating.jar:${HIVE_LIB}/*.jar\""

spark2-submit --deploy-mode ${DEPLOY_MODE} --master ${MASTER} ${EXTERNAL_JARS} --executor-memory 1G --num-executors 4 --executor-cores 2 --class ${SPARK_CLASS} ${SPARK_BUILD_DIR}/${SPARK_JAR} ${HOST} ${PORT}

echo "spark2-submit --deploy-mode ${DEPLOY_MODE} --master ${MASTER} ${EXTERNAL_JARS} --executor-memory 1G --num-executors 4 --executor-cores 2 --class ${SPARK_CLASS} ${SPARK_BUILD_DIR}/${SPARK_JAR} ${HOST} ${PORT}"

<<EOF
## Ejecución por línea de comandos.
spark2-submit --deploy-mode client --master yarn --conf "spark.driver.extraClassPath=/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hbase/hbase-common-1.2.0-cdh5.14.2.jar:/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hbase/hbase-client-1.2.0-cdh5.14.2.jar:/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hbase/hbase-server-1.2.0-cdh5.14.2.jar:/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hbase/hbase-spark-1.2.0-cdh5.14.2.jar:/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hbase/hbase-protocol-1.2.0-cdh5.14.2.jar:/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/jars/htrace-core-3.2.0-incubating.jar:/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hive/lib/*.jar" --conf "spark.executor.extraClassPath=/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hbase/hbase-common-1.2.0-cdh5.14.2.jar:/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hbase/hbase-client-1.2.0-cdh5.14.2.jar:/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hbase/hbase-server-1.2.0-cdh5.14.2.jar:/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hbase/hbase-spark-1.2.0-cdh5.14.2.jar:/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hbase/hbase-protocol-1.2.0-cdh5.14.2.jar:/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/jars/htrace-core-3.2.0-incubating.jar:/opt/cloudera/parcels/CDH-5.14.2-1.cdh5.14.2.p0.3/lib/hive/lib/*.jar" --executor-memory 1G --num-executors 4 --executor-cores 2 --class com.cloudera.sdk.SparkStreamPollingExample ./sparkflumehbase_2.11-1.1.jar ip-172-31-50-30.ec2.internal 41415
EOF
