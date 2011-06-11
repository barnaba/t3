package common

object Chain {
  var redux: ReduxFunction = _
  var hash: HashFunction = _
  var length: Int = _
}

class Chain(first: Array[Byte], startPosition: Int, length: Int) extends Ordered[Chain]{

  val last = element(first,startPosition)
  val lastAsStr = last.map(_.toChar).mkString("")

  def this(first: Array[Byte]) = this(first, 0, Chain.length)
  def this(first: Array[Byte], startPos: Int) = this(first, startPos, Chain.length)

  private def element(key:Array[Byte], n: Int) : Array[Byte] = {
      val hash = Chain.hash(key)
      val redux = Chain.redux(hash, n)
    if (n < length)
      element(redux, n+1)
    else
      return redux
  }

  def compare(that: Chain) = {
    lastAsStr compare that.lastAsStr
  }

  def compact() : Array[Byte] = last ++ " ".getBytes ++ first ++ "\n".getBytes

}
