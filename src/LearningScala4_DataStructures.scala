package com.software.sparkstreaming

object LearningScala4_DataStructures extends App {

  // Data structures

  // Tuples (Also really common with Spark!!)
  // Immutable lists
  // Often though of as database fields, or columns.
  // Useful for passing around entire rows of data.

  val captainStuff = ("Picard", "Enterprise-D", "NCC-1701-D")
                                                            //> captainStuff: (String, String, String)
  println(captainStuff)

  // You refer to individual fields with their ONE-BASED index:
  println(captainStuff._1) //> Picard
  println(captainStuff._2) //> Enterprise-D
  println(captainStuff._3) //> NCC-1701-D

  // You can create a key/value pair with ->
  val picardsShip = "Picard" -> "Enterprise-D" //> picardsShip: (String, String)
  println("key: " + picardsShip._1)
  println("value: " + picardsShip._2)

  // You can mix different types in a tuple
  val aBunchOfStuff = ("Kirk", 1964, true)
  println(aBunchOfStuff)

  // Lists
  // LIke a tuple, but it's an actual Collection object that has more functionality.
  // Also, it cannot hold items of different types.
  // It's a singly-linked list under the hood.

  val shipList = List("Enterprise", "Defiant", "Voyager", "Deep Space Nine")

  // Access individual members using () with ZERO-BASED index (confused yet?)
  println(shipList(1))

  // head and tail give you the first item, and the remaining ones.
  println(shipList.head)
  println(shipList.tail)

  // Iterating though a list
  for (ship <- shipList) {println(ship)}

  // Let's apply a function literal to a list! map() can be used to apply any function to every iter in a  collection.
  val backwardShips = shipList.map( (ship: String) => {ship.reverse})

  for (ship <- backwardShips) {println(ship)}

  // reduce() can be used to combine together all the items in a  collection useing some function.
  val numberList = List(1, 2, 3, 4, 5)
  val sum = numberList.reduce( (x: Int, y: Int) => x + y)
  println(sum)

  // filter() can remove stuff you don't want. Here we'll introduce wildcard syntax while we're at it.
  val iHateFives = numberList.filter((x: Int) => x != 5)
  val iHateThrees = numberList.filter(_ != 3)
  println(iHateFives)
  println(iHateThrees)

  // Note that Spark has its own map, reduce and filter functions that can distribute these operations.

  // Concatenate lists
  val moreNumbers = List(6, 7, 8)
  val lotsOfNumbers = numberList ++ moreNumbers // lotsOfNumbers : List[Int] = List(1, 2, 3, 4, 5, 6, 7, 8)

  // More list fun
  val reversed = numberList.reverse
  val sorted = reversed.sorted
  val lotsOfDuplicates = numberList ++ numberList
  val distinctValues = lotsOfDuplicates.distinct // values without duplicates
  val maxValue = numberList.max
  val total = numberList.sum
  val hashThree = iHateThrees.contains(3)

  println("***" * 100)
  println("reversed " + reversed)
  println("sorted " + sorted)
  println("lotsOfDuplicates " + lotsOfDuplicates)
  println("distinctValues " + distinctValues)
  println("maxValue " + maxValue)
  println("total " + total)
  println("hashThree" + hashThree)

  // Maps
  // Useful for key/value lookups on distinct keys
  // Like dictionaries in other languages

  val shipMap = Map("Kirk" -> "Enterprise", "Picard" -> "Enterprise-D", "Sisko" -> "Deep Space Nine",
                    "Janeway" -> "Voyager")

  println(shipMap("Janeway")) //> Voyager

  // Dealing with missing keys
  println(shipMap.contains("Archer")) //> false

  val archersShip = util.Try(shipMap("Archer")) getOrElse "Unknown"
  println(archersShip)

  def evenNumbers(x: Int): Unit = {
    for (x <- 1 to x if x % 2 == 0) println(x)
  }
  evenNumbers(20)

  def divideBy3Numbers(x: Int): Unit = {
    for (x <- 1 to x if x % 3 == 0) println(x)
  }

  divideBy3Numbers(20)
}
