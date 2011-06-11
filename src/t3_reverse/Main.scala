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
    val hash = org.apache.commons.codec.binary.Hex.decodeHex(args(1).toArray)

    val lines = scala.io.Source.fromFile(metadataFile).getLines.toArray

    val keyLength= lines(0).toInt
    val chainLength = lines(1).toInt
    val alphabet = lines(3)

		Chain.redux = new MD5Redux(alphabet, keyLength)
		Chain.hash = MD5Hash
		Chain.length = chainLength

    val redux = new MD5Redux(alphabet, keyLength)
    val table = new RainbowTable(rainbowFile, keyLength)

    for (level <- 1 to chainLength) {
      Chain.length = chainLength
      var key = redux(hash, level-1)
      if (chainLength != level){
        var chain = new Chain(key, level)
        key = chain.last
      }

      val matching_firsts = table(key)
      matching_firsts foreach { (first : Array[Byte]) =>
              Chain.length = level - 1
              val c = new Chain(first)
              if (table.b2s(MD5Hash(c.last)) == table.b2s(hash))
                println("Znaleziono klucz dla hasha " + args(1) + ": " + table.b2s(c.last))
      }
    }
  }
}
