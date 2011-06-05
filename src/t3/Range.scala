package t3
import scala.math

class KeyRange (alphabet:String, len:Int, range: Range) extends Iterator[String] {

  var current : Int = 0;
  val alen : Int = alphabet.length()

  def this(alphabet:String, len:Int, step:Int) = 
    this(alphabet, len, 0 to math.pow(alphabet.length(), len).toInt by step)

  def next(): String = {
    val value = currentString(range(current), Nil)
    current+=1
    value
  }

  def hasNext: Boolean = current < range.length

  def pop(howMany: Int): KeyRange = {
    val r = new KeyRange(alphabet, len, current to (current + howMany) by range.step)
    current+=howMany
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
