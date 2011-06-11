package t3_hash

import common._

object Main {

  def main(args : Array[String]) : Unit = {
    if(args.length < 1) {
      System.out.println("usage: t3_hash input")
      return
    }

    val key = args(0).getBytes
    println(MD5Hash(key).map(_.toChar).mkString(""))
    }
}
