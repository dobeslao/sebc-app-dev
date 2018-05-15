#!/bin/sh

SPARK_CLASS=com.cloudera.sdk.SparkBatchExample
SPARK_BUILD_DIR=.
SPARK_JAR=sparkbatchexample_2.10-1.1.jar
SPARK_BASE=spark/

DEPLOY_MODE=cluster
MASTER=yarn

spark-submit --deploy-mode ${DEPLOY_MODE} --master ${MASTER} --executor-memory 128M --num-executors 2 --executor-cores 1 --class ${SPARK_CLASS} ${SPARK_BUILD_DIR}/${SPARK_JAR} ${SPARK_BASE}
