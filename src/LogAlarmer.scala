package com.software.sparkstreaming

import Utilities.{apacheLogPattern, setupLogging}

import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.{Seconds, StreamingContext}

import java.util.regex.Matcher

object LogAlarmer {

  def main(args: Array[String]): Unit = {

    // Create the contex with a 1 second batch size
    val scc = new StreamingContext("local[*]", "LogAlarmer", Seconds(1))

    setupLogging()

    // Construct regular expression (regex) to extract fields from raw Apache log lines
    val pattern = apacheLogPattern()

    // Create a socket stream to read log data published via netcat on port 9999 locally
    val lines = scc.socketTextStream("127.0.0.1", 9999, StorageLevel.MEMORY_AND_DISK_SER)

    // Extract the status field from each log line
    val statuses = lines.map(x => {
      val matcher: Matcher =  pattern.matcher(x);
      if (matcher.matches()) matcher.group(6) else "[error]"
    })

    // Now map these status results to success and failure
    val successFailure = statuses.map(x => {
      val statusCode = util.Try(x.toInt) getOrElse 0
      if (statusCode >= 200 && statusCode < 300) {
        "Success"
      } else if (statusCode >= 500 && statusCode < 600) {
        "Failure"
      } else {
        "Other"
      }
    })

    // Tally up statuses over a 5-minute window sliding every second
    val statusCounts = successFailure.countByValueAndWindow(Seconds(300), Seconds(1))

    //For each batch, get the RDD's representing data from out current window
    statusCounts.foreachRDD((rdd, time) => {
      // Keep track of total success and error codes from each RDD
      var totalSuccess: Long = 0
      var totalError: Long = 0

      if (rdd.count() > 0) {
        val elements = rdd.collect()
        for (element <- elements) {
          val result = element._1
          val count = element._2
          if (result == "Success") {
            totalSuccess += count
          }
          if (result == "Failure") {
            totalError += count
          }
        }
      }
    // Print totals from current window
    println("Total success: " + totalSuccess + " Total failure: " + totalError)

    // Don't alarm unless we have some minimum amount data to work with
    if (totalError + totalSuccess > 100) {
      // Compute the error rate
      // Note use of util.Try to handle potential divide by zero exception
      val ratio :Double = util.Try(totalError.toDouble / totalSuccess.toDouble) getOrElse 1.0
      // If there are more errors than successes, wake someone up
      if (ratio > .5) {
        // In real life, you'd use JavaMail or Scala's courier library to send an
        // email that causes somebody's phone to make annoying noises, and you'd
        // make sure there alarms are only sent at most every half hour or something.
        println("Wake somebody up! Something is horribly wrong.")
      } else {
        println("All systems go.")
      }
    }
    })

  // Also in real life, you'd need to monitor the case of your site freezing entirely
  // and traffic stopping. In other words, don't use this script to monitor a real
  // production website! There's more you need.

  // Kick it off
  scc.checkpoint("./cpt")
  scc.start()
  scc.awaitTermination()
  }
}
