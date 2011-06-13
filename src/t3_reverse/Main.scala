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

    val lines = scala.io.Source.fromFile(metadataFile).getLines.toArray

    val keyLength= lines(0).toInt
    val chainLength = lines(1).toInt
    val alphabet = lines(3)

    val canRead= (new java.io.File(rainbowFile).canRead() && new java.io.File(metadataFile).canRead())

    val tableError = if (keyLength < 1) "keyLength too low"
      else if (chainLength < 2) "chainLength too low"
      else if (alphabet.length < 2) "alphabet to small"
      else ""

    val argError = if (! canRead) "can't read provided file"
      else if (args(1).length != 32) "provided hash is too short"
      else ""

    if (argError.length > 0){
      println(argError)
      exit(1)
    }

    if (tableError.length > 0){
      println("Problem with your table file: " + tableError)
      exit(1)
    }


    val hash = org.apache.commons.codec.binary.Hex.decodeHex(args(1).toArray)




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
              val c = new Chain(first, 0, level-2)
              if (table.b2s(MD5Hash(c.last)) == table.b2s(hash)){
                println("Znaleziono klucz dla hasha " + args(1) + ": " + table.b2s(c.last))
                exit()
              }
              if(table.b2s(MD5Hash(first)) == table.b2s(hash)){
                println("Znaleziono klucz dla hasha " + args(1) + ": " + table.b2s(first))
                exit()
                }
      }
    }
    println("Nie znaleziono pasujacego klucza")
  }
}
