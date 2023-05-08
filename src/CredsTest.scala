package com.software.sparkstreaming

import org.json4s.scalap.scalasig.ClassFileParser.fields

object CredsTest extends App {

  def setupTwitter() = {
    import scala.io.Source

    for (line <- Source.fromFile("twitter.txt").getLines) {
      val fields = line.split(" ")
      println("twitter4j.oauth." + fields(0), fields(1))
      if (fields.length == 2) {
        System.setProperty("twitter4j.oauth." + fields(0), fields(1))
      }
    }
  }
  setupTwitter()
}
