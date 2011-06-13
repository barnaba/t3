package common
import scala.math


class MD5Redux(alphabet: String, keyLength: Int) extends ReduxFunction {

  val alen = alphabet.length()
  val alenSquared = math.pow(alen,2).toInt
  val shift = (scala.math.log10(alen)/scala.math.log10(2)).toInt

  def apply(input: Array[Byte], level: Int): Array[Byte] = {
      assert(input.length >= 16)
      val indices = new Array[Int](16)
      for (i <- 0 to 15 by 4){
          val h = (getInt(input.slice(i*4,(i+1)*4)) ^ (level << shift)).abs
          indices(i) = ((h % alenSquared ) / alen)
          indices(i+1) = ((h % alenSquared) % alen)
          indices(i+2) = (((h / alenSquared) % alenSquared) / alen)
          indices(i+3) = (((h / alenSquared) % alenSquared) / alen)
      }

      assert(indices.filter(_ > alen).length == 0)
      indices.slice(0,keyLength).map(i => alphabet(i)).mkString("").getBytes()
  }

  private def getInt(bytes: Array[Byte]): Int = {
      var result = 0
      for (i <- bytes.indices) {
          result += (bytes(i) << i*8)
      }
      result
  }
}
