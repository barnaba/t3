import org.scalatest.FunSuite
import org.scalatest.matchers.ShouldMatchers

//this test documents generation and writing of hash chains and serves as a smoke test


class DocuText extends FunSuite {

  test("hash generation produces existing file") {

    val alphabet = "ab"
    val keyLength = 2
    val chainLength = 5
    val step = 1
    val range = t3.KeyRange(alphabet, keyLength, step);
    // this has to be done only once, possibly in main()
    {
      //Chain companion object must be properly initialized with
      //HashFunction and ReduxFunction instances
      common.Chain.redux = new common.MD5Redux(alphabet, keyLength)
      common.Chain.hash = common.MD5Hash
      common.Chain.length = chainLength
    }
  
    //this has to be done for every range - message from manager
    val factory = new t3.ChainFactory(range)
    val file = factory.filename

    assert(file.exists())
    assert(file.length === 16)
    file.delete()
  }
  
}


