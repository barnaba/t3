package t3_reverse
import common._

class RainbowTable (rainbowFile: String, keyLength: Int){
  val separatorLength = 1

  val chain = new Chain(("a"*keyLength).getBytes)
    val bytesPerChain = chain.compact.length
  val file = new java.io.File(rainbowFile)
    val chains = file.length / bytesPerChain
  val stream = new java.io.FileInputStream(file)

  def apply(key: Array[Byte]): Array[Byte] = {
    val keyString = b2s(key)
    var first = 0
    var upto = chains

    while (first < upto){
      val mid = ((first + upto) / 2).toInt
      val midChain = getNthChain(mid)
      val midKey = midChain._2

      if (keyString  < midKey)
        upto = mid
      else if (keyString > midKey)
        first = mid
      else
        return midChain._1.getBytes
    }
    return "".getBytes
  }

  //This is useful in some other places (Chain), consider refactoring
  //byte array to string
  def b2s(bytes: Array[Byte]) = bytes.map(_.toChar).mkString("")

  def getNthChain(n: Int) = {
    val first = new Array[Byte](keyLength)
    val last = new Array[Byte](keyLength)

    stream.read(last, n*bytesPerChain, keyLength)
    stream.read(first, n*bytesPerChain + keyLength + separatorLength, keyLength)

    (b2s(first), b2s(last))
  }


}
