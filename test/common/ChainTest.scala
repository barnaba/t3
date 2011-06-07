package common

import org.scalatest.FunSuite

class commonSuite extends FunSuite {

  test("Smoketest") {
      Chain.redux = new MD5Redux("abcdefg", 10)
      Chain.hash = MD5Hash
      Chain.length = 10
      val c = new Chain("fasada".getBytes)
  }
}
