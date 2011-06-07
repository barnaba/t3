import org.scalatest.FunSuite
import t3.KeyRange

class KeyRangeSuite extends FunSuite {

  test("next method works") {
    val r = KeyRange("01", 4, 1)
    assert("0000" == r.next())
    assert("0001" == r.next())
    assert("0010" == r.next())
  }

  test("step in next works") {
    val r = KeyRange("01", 4, 4)
    assert("0000" == r.next())
    assert("0100" == r.next())
    assert("1000" == r.next())
  }

  test("poped Range starting position") {
    val r = KeyRange("01", 4, 1)
    r.next()
    r.pop(10)
    val k = r.pop(10)
    // eleventh string
    assert(k.next() == "1011")
  }
  
}

