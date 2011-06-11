package common
import scala.math


class MD5Redux(alphabet: String, len: Int) extends ReduxFunction {
  implicit def double2int(d:Double): Int = d.toInt

  val alen = alphabet.length()
  val shift = scala.math.log10(alen)/scala.math.log10(2)

  def apply(input: Array[Byte], level: Int): Array[Byte] = {
      assert(input.length >= 16)
      val indices = new Array[Int](16)
      for (i <- 0 to 9 by 3){
          val h = (getInt(input.slice(i*4,(i+1)*4)) ^ (level << shift)).abs
          indices(i) = (h % math.pow(alen,2)) / alen
          indices(i+1) = (h % math.pow(alen,2)) % alen
          indices(i+2) = ((h / math.pow(alen,2)) % math.pow(alen,2)) / alen
      }
      assert(indices.filter(_ > alen).length == 0)
      indices.slice(0,len).map(i => alphabet(i)).mkString("").getBytes()
  }

  private def getInt(bytes: Array[Byte]): Int = {
      var result = 0
      for (i <- bytes.indices) {
          result += (bytes(i) << i*8)
      }
      result
  }
}
