package t3_reverse
import common._

class RainbowTable (rainbowFile: String, keyLength: Int){
  val separatorLength = 1

  val chain = new Chain(("a"*keyLength).getBytes)
    val bytesPerChain = chain.compact.length
  val file = new java.io.File(rainbowFile)
    val chains = (file.length / bytesPerChain).toLong
  val stream = new java.io.FileInputStream(file)

  def apply(key: Array[Byte]): Array[Byte] = {
    val keyString = b2s(key)
    var low = 0L
    var high = chains - 1
    bsearch(low, high, b2s(key))
  }

  def bsearch(low:Long, high:Long, keyString:String) : Array[Byte]= {
    if (low > high)
      return null
    
    val mid = ((low+high)/2).toLong
    val midChain = getNthChain(mid)
    val midKeyString = b2s(midChain._2)

    if (keyString < midKeyString)
      bsearch(low, mid-1, keyString)
    else if (keyString > midKeyString)
      bsearch(mid+1, high, keyString)
    else{
      return midChain._1
    }
  }

  //This is useful in some other places (Chain), consider refactoring
  //byte array to string
  def b2s(bytes: Array[Byte]) = bytes.map(_.toChar).mkString("")

  def getNthChain(n: Long) = {
    val chain = new Array[Byte](bytesPerChain)

    stream.skip(n.toLong*bytesPerChain)
    stream.read(chain)
    stream.skip(-(n+1).toLong*bytesPerChain)
    val last = chain.slice(0, keyLength)
    val first = chain.slice(keyLength + separatorLength, bytesPerChain-1) 

    (first, last)
  }


}
