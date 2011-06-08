package t3

import org.scalatest.FunSuite
import java.io.{File, FileWriter}
import scala.io.Source

class FileMergerTest extends FunSuite {
	var files = 1 to 2 map { _ => File.createTempFile("ttt", "test") }
	val writers = files map { new FileWriter(_) }
	
	writers(0).write(List("aa", "bb", "cc") mkString "\n")
	writers(1).write(List("ab", "ac", "ba", "bc", "ca", "cb") mkString "\n")
	
	writers foreach { _.close }
	
	test("merging files") {
		val paths = files map { _.getAbsolutePath }
		val merger = new FileMerger(paths(0), paths(1))
		
		val expected = List("aa", "ab", "ac", "ba", "bb", "bc", "ca", "cb", "cc")
		val result = Source.fromFile(merger.filename).getLines.toList
		
		assert(result === expected)
		
		files foreach { _.delete }
		new File(merger.filename).delete
	}
}

object FileMergerTestRunner extends Application {
	(new FileMergerTest).execute()
}
