package com.software.sparkstreaming

object LearningScala3_Functions extends App {

  // Functions
  // Format is def <function name> (parameter name: type...) : return type = { expression }
  // Don't forget the = before expression!

  def squareIt (x: Int): Int = {
    x * x
  }

  def cubeIt(x: Int): Int = {x * x * x}
  println(squareIt(2))
  println(cubeIt(2))

  // Functions can take other functions as parameters
  def transformInt (x: Int, f: Int => Int): Int = {
    f(x)
  }
  val result = transformInt(2, cubeIt)
  println("The result is: " + result) //> 8

  // "Lambda functions", "anonymous functions", "functions literals"
  // You can declare functions inline without even giving them a name
  // This happens a lot in Spark.
  val res0 = transformInt(3, x => x * x * x) // res0: Int = 27
  val res1 = transformInt(10, x => x / 2) // res1: Int = 5
  val res3 = transformInt(2, x => {val y = x * 2; y * y}) //res3: Int = 16
  println(res0)
  println(res1)
  println(res3)

  val stringToConvert = "anystring"

  private def toUpper(str: String): String = {
    str.toUpperCase()
  }

  println(toUpper(stringToConvert))

}
