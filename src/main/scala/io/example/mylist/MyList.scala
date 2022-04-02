package io.example.mylist

import scala.annotation.tailrec

sealed abstract class MyList[+A] {
  def head: A
  def tail: MyList[A]
  def isEmpty: Boolean
  def add[B >: A](elem: B): MyList[B]
  def printElements: String
  override def toString: String = s"[$printElements]"

  // concatenation
  def ++[B >: A](list: MyList[B]): MyList[B]

  def map[B](f: A => B): MyList[B]
  def flatMap[B](f: A => MyList[B]): MyList[B]
  def filter(p: A => Boolean): MyList[A]

  def foreach(f: A => Unit): Unit
  def sort(compare: (A, A) => Int): MyList[A]
  def zipWith[B, C](list: MyList[B], zip: (A, B) => C): MyList[C]
  def fold[B](start: B)(operator: (B, A) => B): B

  def reverse: MyList[A]
}

case class Cons[+Z](h: Z, t: MyList[Z]) extends MyList[Z] {
  def head: Z = h
  def tail: MyList[Z] = t
  def isEmpty: Boolean = false
  def add[B >: Z](elem: B): MyList[B] = Cons(elem, Cons(head, tail))
  def printElements: String = s"$h ${t.printElements}"

  def ++[D >: Z](list: MyList[D]): MyList[D] = Cons(h, t ++ list)

  def map[B](f: Z => B): MyList[B] = Cons(f(h), t.map(f))
  def flatMap[B](f: Z => MyList[B]): MyList[B] = f(h) ++ t.flatMap(f)
  def filter(p: Z => Boolean): MyList[Z] =
    if (p(h)) Cons(h, t.filter(p))
    else t.filter(p)

  def foreach(f: Z => Unit): Unit = {
    f(h)
    t.foreach(f)
  }

  def sort(compare: (Z, Z) => Int): MyList[Z] = {
    def insert(x: Z, sortedList: MyList[Z]): MyList[Z] = {
      if (sortedList.isEmpty) Cons(x, Empty)
      else if (compare(x, sortedList.head) <= 0) Cons(x, sortedList)
      else Cons(sortedList.head, insert(x, sortedList.tail))
    }
    insert(h, t.sort(compare))
  }

  def zipWith[B, C](list: MyList[B], zip: (Z, B) => C): MyList[C] = {
    if (list.isEmpty) throw new RuntimeException("Lists do not have the same length")
    else Cons(zip(h, list.head), t.zipWith(list.tail, zip))
  }

  def fold[B](start: B)(op: (B, Z) => B): B =
    t.fold(op(start, h))(op)

  def reverse: MyList[Z] = {
    @tailrec
    def reverseTR(list: MyList[Z], accList: MyList[Z]): MyList[Z] = {
      if (list.isEmpty) accList
      else reverseTR(list.tail, Cons(list.head, accList))
    }

    reverseTR(this, Empty)
  }

}

case object Empty extends MyList[Nothing] {
  def head: Nothing = throw new NoSuchElementException
  def tail: MyList[Nothing] = throw new NoSuchElementException
  def isEmpty: Boolean = true
  def add[B >: Nothing](elem: B): MyList[B] = Cons(elem, Empty)
  def printElements: String = "Empty"

  def ++[D >: Nothing](list: MyList[D]): MyList[D] = list

  def map[B](f: Nothing => B): MyList[B] = this
  def flatMap[B](f: Nothing => MyList[B]): MyList[B] = this
  def filter(p: Nothing => Boolean): MyList[Nothing] = this

  def foreach(f: Nothing => Unit): Unit = ()
  def sort(compare: (Nothing, Nothing) => Int): MyList[Nothing] = this
  def zipWith[B, C](list: MyList[B], zip: (Nothing, B) => C): MyList[Nothing] = {
    if (!list.isEmpty) this
    else this
  }
  def fold[B](start: B)(op: (B, Nothing) => B): B = start

  def reverse: MyList[Nothing] = this
}

object MyList {

  def apply[A](elems: A*): MyList[A] = {

    @tailrec
    def recursiveBuild[B >: A](seq: Seq[B], myList: MyList[B]): MyList[B] = {
      if (seq.isEmpty) myList
      else recursiveBuild(seq.tail, myList.add(seq.head))
    }

    recursiveBuild(elems, Empty)

  }

}

