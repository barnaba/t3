package t3_hash

import common._

object Main {

  def main(args : Array[String]) : Unit = {
    if(args.length < 1) {
      System.out.println("usage: t3_hash input")
      return
    }

    val key = args(0).getBytes
    val hash = MD5Hash(key)
    val hashText = org.apache.commons.codec.binary.Hex.encodeHex(hash)
    println(hashText.mkString(""))
    }
}
