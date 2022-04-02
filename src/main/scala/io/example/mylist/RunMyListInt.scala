package io.example.mylist

object RunMyListInt extends App {

  val mlSingleElement: MyList[Int] = MyList(42)
  val ml1: MyList[Int] = MyList(4,2,9,5,1)
  val ml2: MyList[Int] = MyList(10,-2,4,6)
  val ml3: MyList[Int] = MyList(3,1,10,5,2)
  val mlEmpty: MyList[Int] = MyList()

  val mlConcat: MyList[Int] = ml1 ++ ml2 ++ mlEmpty

  val ml1Times2: MyList[Int] = ml1.map(_ * 2)
  val ml1Times3: MyList[Int] = ml1.flatMap(x => MyList(x * 3))
  val ml1Filtered: MyList[Int] = ml1.filter(_ > 3)
  val ml1SortedAsc: MyList[Int] = ml1.sort((x, y) => x - y)
  val ml1SortedDesc: MyList[Int] = ml1.sort((x, y) => y - x)
  val ml1ml3Zipped: MyList[Int] = ml1.zipWith(ml3, (x: Int, y: Int) => x + y)
  val ml1Reversed: MyList[Int] = ml1.reverse

  println(s"mlSingleElement: $mlSingleElement")
  println(s"ml1: $ml1")
  println(s"ml2: $ml2")
  println(s"ml3: $ml3")
  println(s"mlEmpty: $mlEmpty")
  println(s"mlConcat: $mlConcat")

  println(s"ml1Times2: $ml1Times2")
  println(s"ml1Times3: $ml1Times3")
  println(s"ml1Filtered: $ml1Filtered")
  ml1.foreach(x => println(s"elem=$x"))
  println(s"ml1SortedAsc: $ml1SortedAsc")
  println(s"ml1SortedDesc: $ml1SortedDesc")
  println(s"ml1ml3Zipped: $ml1ml3Zipped")
  println(s"ml1Reversed: $ml1Reversed")

  /*

    Step-by-step of concatenation of two MyLists:
    Cons(1, Cons(5, Cons(9, Cons(2, Cons(4, Empty))))) ++ Cons(6, Cons(4, Cons(-2, Cons(10, Empty))))
    Cons[1, Cons(5, Cons(9, Cons(2, Cons(4, Empty)))) ++ Cons(6, Cons(4, Cons(-2, Cons(10, Empty))))]
    Cons[1, Cons[5, Cons(9, Cons(2, Cons(4, Empty))) ++ Cons(6, Cons(4, Cons(-2, Cons(10, Empty))))]]
    Cons[1, Cons[5, Cons[9, Cons(2, Cons(4, Empty)) ++ Cons(6, Cons(4, Cons(-2, Cons(10, Empty))))]]]
    Cons[1, Cons[5, Cons[9, Cons[2, Cons(4, Empty) ++ Cons(6, Cons(4, Cons(-2, Cons(10, Empty))))]]]]
    Cons[1, Cons[5, Cons[9, Cons[2, Cons[4, Empty ++ Cons(6, Cons(4, Cons(-2, Cons(10, Empty))))]]]]]
    Cons[1, Cons[5, Cons[9, Cons[2, Cons[4, Cons(6, Cons(4, Cons(-2, Cons(10, Empty))))]]]]]

    Step-by-step of flatMap using function f:
    Cons(1, Cons(5, Cons(9, Cons(2, Cons(4, Empty))))).flatMap(f)
    f(1) ++ Cons(5, Cons(9, Cons(2, Cons(4, Empty)))).flatMap(f)
    f(1) ++ f(5) ++ Cons(9, Cons(2, Cons(4, Empty))).flatMap(f)
    f(1) ++ f(5) ++ f(9) ++ Cons(2, Cons(4, Empty)).flatMap(f)
    f(1) ++ f(5) ++ f(9) ++ f(2) ++ Cons(4, Empty).flatMap(f)
    f(1) ++ f(5) ++ f(9) ++ f(2) ++ f(4) ++ Empty.flatMap(f)
    f(1) ++ f(5) ++ f(9) ++ f(2) ++ f(4) ++ Empty
    Cons(1, Empty) ++ Cons(5, Empty) ++ Cons(9, Empty) ++ Cons(2, Empty) ++ Cons(4, Empty) ++ Empty
    Cons(1, Empty) ++ Cons(5, Empty) ++ Cons(9, Empty) ++ Cons(2, Empty) ++ Cons(4, Empty)
    Cons(1, Empty ++ [Cons(5, Empty) ++ Cons(9, Empty) ++ Cons(2, Empty) ++ Cons(4, Empty)])
    Cons(1, [Cons(5, Empty) ++ Cons(9, Empty) ++ Cons(2, Empty) ++ Cons(4, Empty)])
    Cons(1, [Cons(5, Empty ++ [Cons(9, Empty) ++ Cons(2, Empty) ++ Cons(4, Empty])])
    Cons(1, [Cons(5, [Cons(9, Empty) ++ Cons(2, Empty) ++ Cons(4, Empty])])
    Cons(1, [Cons(5, [Cons(9, Empty ++ Cons(2, Empty) ++ Cons(4, Empty)]])
    Cons(1, [Cons(5, [Cons(9, Cons(2, Empty ++ Cons(4, Empty)]])
    Cons(1, [Cons(5, [Cons(9, Cons(2, Cons(4, Empty)]])

    Step-by-step of filter using function p:
    Cons(1, Cons(5, Cons(9, Cons(2, Cons(4, Empty))))).filter(p)
    Cons(5, Cons(9, Cons(2, Cons(4, Empty)))).filter(p)
    Cons[5, Cons(9, Cons(2, Cons(4, Empty))).filter(p)]
    Cons[5, Cons[9, Cons(2, Cons(4, Empty)).filter(p)]]
    Cons[5, Cons[9, Cons(4, Empty).filter(p)]]
    Cons[5, Cons[9, Cons[4, Empty.filter(p)]]]
    Cons[5, Cons[9, Cons[4, Empty]]]

    Step-by-step of foreach using function f:
    Cons(1, Cons(5, Cons(9, Cons(2, Cons(4, Empty))))).foreach(f)
    f(1)
    Cons(5, Cons(9, Cons(2, Cons(4, Empty)))).foreach(f)
    f(5)
    Cons(9, Cons(2, Cons(4, Empty))).foreach(f)
    f(9)
    Cons(2, Cons(4, Empty)).foreach(f)
    f(2)
    Cons(4, Empty).foreach(f)
    f(4)
    Empty.foreach(f)
    ()

    Step-by-step of sort using function compare:
    Cons(1, Cons(5, Cons(9, Cons(2, Cons(4, Empty))))).sort(compare)
    insert[1, Cons(5, Cons(9, Cons(2, Cons(4, Empty)))).sort(compare)]
    insert[1, insert[5, Cons(9, Cons(2, Cons(4, Empty))).sort(compare)]]
    insert[1, insert[5, insert[9, Cons(2, Cons(4, Empty)).sort(compare)]]]
    insert[1, insert[5, insert[9, insert[2, Cons(4, Empty).sort(compare)]]]]
    insert[1, insert[5, insert[9, insert[2, insert[4, Empty.sort(compare)]]]]]
    insert[1, insert[5, insert[9, insert[2, insert[4, Empty]]]]]
    insert[1, insert[5, insert[9, insert[2, Cons(4, Empty)]]]]
    insert[1, insert[5, insert[9, Cons(2, Cons(4, Empty))]]]

    Step-by-step of reversing list:
    Cons(1, Cons(5, Cons(9, Cons(2, Cons(4, Empty))))).reverse
    reverseTR[Cons(1, Cons(5, Cons(9, Cons(2, Cons(4, Empty))))), Empty]
    reverseTR[Cons(5, Cons(9, Cons(2, Cons(4, Empty)))), Cons(1, Empty)]
    reverseTR[Cons(9, Cons(2, Cons(4, Empty))), Cons(5, Cons(1, Empty))]
    reverseTR[Cons(2, Cons(4, Empty)), Cons(9, Cons(5, Cons(1, Empty)))]
    reverseTR[Cons(4, Empty), Cons(2, Cons(9, Cons(5, Cons(1, Empty))))]
    reverseTR[Empty, Cons(4, Cons(2, Cons(9, Cons(5, Cons(1, Empty)))))]
    Cons(4, Cons(2, Cons(9, Cons(5, Cons(1, Empty)))))

    Reversing list broken down a bit further:
    Decomposing "list" variable
    1 :: Cons(5, Cons(9, Cons(2, Cons(4, Empty))))
    1 :: 5 :: Cons(9, Cons(2, Cons(4, Empty)))
    1 :: 5 :: 9 :: Cons(2, Cons(4, Empty))
    1 :: 5 :: 9 :: 2 :: Cons(4, Empty)
    1 :: 5 :: 9 :: 2 :: 4 :: Empty

    Building up "accList" variable by taking the head of the decomposed "list"
    5 :: 9 :: 2 :: 4 :: Cons(1, Empty)
    9 :: 2 :: 4 :: Cons(5, Cons(1, Empty))
    2 :: 4 :: Cons(9, Cons(5, Cons(1, Empty)))
    4 :: Cons(2, Cons(9, Cons(5, Cons(1, Empty))))
    Cons(4, Cons(2, Cons(9, Cons(5, Cons(1, Empty)))))

   */
}
