package t3_reverse

import common._

object Main {
  def main(args : Array[String]) : Unit = {
    if(args.length < 2) {
      System.out.println("usage: t3_reverse input hash")
      return
    }

    val rainbowFile = args(0) + ".rt"
    val metadataFile = args(0) + ".rtm"
    val hash = args(1)

    val lines = scala.io.Source.fromFile(metadataFile).mkString.split("\n")

    val keyLength= lines(0).toInt
    val chainLength = lines(1).toInt
    val alphabet = lines(3)

		Chain.redux = new MD5Redux(alphabet, keyLength)
		Chain.hash = MD5Hash
		Chain.length = chainLength

    val redux = new MD5Redux(alphabet, keyLength)
    val table = new RainbowTable(rainbowFile, keyLength)

    for (level <- 0 to chainLength) {
      var key = redux(hash.getBytes, chainLength - level)
      for (i <- 1 to level){
        val hash = MD5Hash(key)
        key = redux(hash, chainLength -i)
      }

      val first = table(key)
      if (first != "".getBytes)
        println(first)

    }
  }
}
