package common

object Chain {
  var redux: ReduxFunction = _
  var hash: HashFunction = _
  var length: Int = _
}

class Chain(first: Array[Byte]) extends Ordered[Chain]{

  val last = element(first,0)
  val lastAsStr = last.map(_.toChar).mkString("")

  private def element(key:Array[Byte], n: Int) : Array[Byte] = {
      val hash = Chain.hash(key)
      val redux = Chain.redux(hash, n)
    if (n < Chain.length)
      element(redux, n+1)
    else
      return redux
  }

  def compare(that: Chain) = {
    lastAsStr compare that.lastAsStr
  }

  def compact() : Array[Byte] = first ++ last

}
