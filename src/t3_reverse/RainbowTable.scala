package t3_reverse
import common._

class RainbowTable (rainbowFile: String, keyLength: Int){
  val separatorLength = 1

  val chain = new Chain(("a"*keyLength).getBytes)
    val bytesPerChain = chain.compact.length
  val file = new java.io.File(rainbowFile)
    val chains = (file.length / bytesPerChain).toLong
  val stream = new java.io.FileInputStream(file)

  def apply(key: Array[Byte]): List[Array[Byte]] = {
    val keyString = b2s(key)
    var low = 0L
    var high = chains - 1
    val m = bsearch(low, high, keyString)
    getConflicting(m, 1, keyString) ::: getConflicting(m, -1, keyString)
  }

  def getConflicting(index: Long, step:Int, keyString: String) : List[Array[Byte]]= {
    if (index < 0 || index > chains || b2s(getNthChain(index)._2) != keyString )
      Nil
    else
      getNthChain(index)._1 :: getConflicting(index+step, step, keyString)
  }

  def bsearch(low:Long, high:Long, keyString:String) : Long = {
    if (low > high)
      return -1
    val mid = ((low+high)/2).toLong
    val midChain = getNthChain(mid)
    val midKeyString = b2s(midChain._2)

    if (keyString < midKeyString)
      bsearch(low, mid-1, keyString)
    else if (keyString > midKeyString)
      bsearch(mid+1, high, keyString)
    else{
      return mid
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
