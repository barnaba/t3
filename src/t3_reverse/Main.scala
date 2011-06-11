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
    val hash = args(1).getBytes

    val lines = scala.io.Source.fromFile(metadataFile).getLines.toArray

    val keyLength= lines(0).toInt
    val chainLength = lines(1).toInt
    val alphabet = lines(3)

		Chain.redux = new MD5Redux(alphabet, keyLength)
		Chain.hash = MD5Hash
		Chain.length = chainLength

    val redux = new MD5Redux(alphabet, keyLength)
    val table = new RainbowTable(rainbowFile, keyLength)

    for (level <- 0 to chainLength) {
      var key = redux(hash, chainLength - level)
      for (i <- level-1 to 0 by -1){
        val hash = MD5Hash(key)
        key = redux(hash, chainLength -i)
      }

      val first = table(key)
      if (first != null){
        for(i <- 0 to chainLength){
          Chain.length = i
          val c = new Chain(first)
          if (table.b2s(MD5Hash(c.last)) == table.b2s(hash))
            println("Znaleziony klucz to " + table.b2s(c.last))
        }
      }
    }
  }
}
