package com.cloudera.sdk

import org.apache.spark.sql.SparkSession
import org.apache.spark.{ SparkConf, SparkContext }
import org.apache.spark.ml.regression.{LinearRegression, LinearRegressionModel}
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{ FileSystem, Path }
import org.apache.spark.rdd.RDD
import org.apache.log4j.{ Level, Logger }

object SparkMLExample {  
  def main(args: Array[String]): Unit = {
    if (args.length < 2) {
      System.err.println("Usage: SparkMLExample <input> <iterations>")
      System.exit(1)
    }

    val path = args(0)
    val iters = args(1).toInt
    val conf = new SparkConf()
      .setAppName("Spark ML Example")
//            .setMaster("local[2]")
      .set("spark.io.compression.codec", "lz4")
    val sc = new SparkContext(conf)

    conf.set("spark.cleaner.ttl", "120000")
    //      conf.set("spark.driver.allowMultipleContexts", "true")

    val spark = SparkSession
      .builder()
      .config(conf)
      .getOrCreate()

    Logger.getRootLogger.setLevel(Level.WARN)

    val training = spark.read.format("libsvm").load(path + "/input/file*csv")

    /* ADD LINEAR REGRESSION FROM SPARKML AND PRINT RESULTS */
    val lr = new LinearRegression()
      .setMaxIter(iters)
      .setRegParam(0.3)
      .setElasticNetParam(0.8)

    // Fit the model
    val lrModel = lr.fit(training)

    // Print the coefficients and intercept for linear regression
    println(s"Coefficients: ${lrModel.coefficients} Intercept: ${lrModel.intercept}")

    // Summarize the model over the training set and print out some metrics
    val trainingSummary = lrModel.summary
    println(s"numIterations: ${trainingSummary.totalIterations}")
    println(s"objectiveHistory: [${trainingSummary.objectiveHistory.mkString(",")}]")
    trainingSummary.residuals.show()
    println(s"RMSE: ${trainingSummary.rootMeanSquaredError}")
    println(s"r2: ${trainingSummary.r2}")

    println(s"predicted_house_price = ${lrModel.intercept} + (${lrModel.coefficients(0)}) * num_of_rooms + (${lrModel.coefficients(1)}) * num_of_baths + (${lrModel.coefficients(2)}) * size_of_house + (${lrModel.coefficients(3)}) * one_if_pool_zero_otherwise")

    // Test prediction.
    val house_prediction = Array(
            Array(5.0, 3.5, 4200.0, 1.0),
            Array(5.0, 3.5, 4200.0, 0.0),
            Array(5.0, 1.0, 4200.0, 1.0),
            Array(5.0, 1.0, 4200.0, 0.0),
            Array(4.0, 3.5, 4200.0, 1.0),
            Array(3.0, 3.5, 4200.0, 1.0),
            Array(2.0, 3.5, 4200.0, 1.0),
            Array(1.0, 3.5, 4200.0, 1.0),
            Array(5.0, 25.0, 4200.0, 1.0)
      )

      house_prediction.foreach {
          hp => 
            val predicted_house_price = lrModel.intercept + (lrModel.coefficients(0)) * hp(0) + (lrModel.coefficients(1)) * hp(1) + (lrModel.coefficients(2)) * hp(2) + (lrModel.coefficients(3)) * hp(3)
            println(s"predicted_house_price = ${lrModel.intercept} + (${lrModel.coefficients(0)}) * ${hp(0)} + (${lrModel.coefficients(1)}) * ${hp(1)} + (${lrModel.coefficients(2)}) * ${hp(2)} + (${lrModel.coefficients(3)}) * ${hp(3)}")
            println(s"${predicted_house_price}")
      }
      
  }
}
