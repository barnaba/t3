package t3
import scala.math

object KeyRange {
  def apply(alphabet: String, len:Int, step:Int) : KeyRange = {
    val last = math.pow(alphabet.length(), len).toInt - 1
    new KeyRange(alphabet, len, 0 to last by step)
  }
}

class KeyRange(alphabet:String, len:Int, range: Range) extends Iterator[String] {

  var current : Int = 0;
  val alen : Int = alphabet.length()

  def next(): String = {
    val value = currentString(range(current), Nil)
    current+=1
    value
  }

  def hasNext: Boolean = current < range.length

  override def length: Int = range.length

  def pop(howMany: Int): KeyRange = {
    val end = if ((current + howMany) > range.length){
      range.length
    } else {
      howMany + current
    }
    val r = new KeyRange(alphabet, len, current to end by range.step)
    current = end
    return r
  }

  private def currentString(current:Int, chars:List[Char]) : String = {
    val char = alphabet(current % alen)
    val nextChars = char :: chars
    val next = current / alen

    if (next == 0){
      return alphabet(0).toString() * (len - nextChars.length) + nextChars.mkString("")
    } else {
      currentString(next, nextChars)
    }
  }
}
