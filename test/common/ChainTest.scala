package common

import org.scalatest.FunSuite

class ChainTest extends FunSuite {

  test("Smoketest") {
      Chain.redux = new MD5Redux("abcdefg", 10)
      Chain.hash = MD5Hash
      Chain.length = 10
      val c = new Chain("fasada".getBytes)
  }
}

object ChainTestRunner extends Application {
    (new ChainTest).execute()
}
