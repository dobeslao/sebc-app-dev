package com.cloudera.sdk

import org.apache.spark.{SparkConf, SparkContext }
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.rdd.RDD

object SparkBatchExample {
    def main(args: Array[String]): Unit = {
        if (args.length < 1) {
            System.err.println("Usage: SparkBatchExample <path>")
            System.exit(1)
        }

        val path = args(0)
        val conf = new SparkConf()
            .setAppName("Spark Batch Example")
            // .setMaster("local[2]")
            .set("spark.io.compression.codec", "lz4")
        val sc = new SparkContext(conf)
 
        conf.set("spark.cleaner.ttl", "120000")
        
        /* CLEAN HDFS OUTPUT FOLDER */
        val hdfs = FileSystem.get(sc.hadoopConfiguration)
        val basePath = new Path(path + "/output/")
        hdfs.delete(basePath, true)

        /* READ DATA FROM HDFS, SORT BY KEY, SAVE TO HDFS */
        val file = sc.textFile(path + "/data/file*.csv")
        val tuple = file.map(line => line.split(","))
                 .map(array => (array(0), array(1)))
        val reducer = tuple.coalesce(1, true)
        val sorted = reducer.sortByKey()
        
        // Save sorted results
        sorted.saveAsTextFile(path + "/output/")
    }
}