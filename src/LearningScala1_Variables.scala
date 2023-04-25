package com.software.sparkstreaming

object LearningScala1_Variables extends App {

  val hello: String = "Hola!"
  println(hello)

  // VARIABLES are mutable
  var helloThere: String = hello
  helloThere = hello + " There!"
  println(helloThere)

  val immutableHelloThere = hello + "There"
  println(immutableHelloThere)
  val moreStruff = immutableHelloThere + "yeah"

  // Some other types
  val numberOne: Int = 1
  val truth: Boolean = true
  val letterA: Char = 'a'
  val pi: Double = 3.14159265
  val piSinglePrecision: Float = 3.14159265f
  val bigNumber: Long = 1234567890
  val smallNumber: Byte = 127

  // String printing trics
  // Concatenating stuff with +:
  println("Here is a mess: " + numberOne + truth + letterA + pi + bigNumber)

  // printf style:
  println(f"Pi is about $piSinglePrecision%.3f") // > Pi is about 3.142
  println(f"Zero padding on the left: $numberOne%05d") // > Zero padding on the left

  // Substituting in variables:
  println(s"I can use the s prefix to use variables like $numberOne $truth $letterA")

  // Substituting expressions (with curly brackets):
  println(s"The s prefix isn't limited to variables; I van include any expression. Like ${1+2}")

  // Using regular expressions:
  val theUltimateAnswer: String = "To life, the universe, and everything is 42."
  val pattern = """.* ([\d]+).*""".r // > pattern: scala.util.matching.Regex = .* ([\d]+).*
  val pattern(answerString) = theUltimateAnswer // > answerString: String 42
  val answer = answerString.toInt // answer: Int = 42
  println(answer)

  // Dealing with booleans
  val isGreater = 1 > 2 // > isGreater: Boolean = false
  val isLesser = 1 < 2  // > isLesser: Boolean = true
  val impossible = isGreater & isLesser // > impossible: Boolean = false
  println(isGreater, isLesser, impossible)

  val picard: String = "Picard"
  val bestCapitan: String = "Picard"
  val isBest: Boolean = picard == bestCapitan // > isBest: Boolean = true

  println(s"Print doubled pi ${pi * 2}")


}
