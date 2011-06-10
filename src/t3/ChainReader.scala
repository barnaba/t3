package t3

import scala.io.Source

class ChainReader(filename: String) {
	private val source = Source.fromFile(filename)
	
	private val lines = source.getLines
	private var line = if(lines.hasNext) lines.next else null
	
	def pop(): String = {
		val result = line
		line = if(lines.hasNext) lines.next else null
		result
	}
	
	def hasNext(): Boolean =
		line != null
	
	def popMinimal(other: ChainReader): String =
    if (this.hasNext && other.hasNext)
      if(this.line < other.line) this.pop else other.pop
    else 
      (if (this.hasNext) this else other).pop
	
	def delete {
		source.close
    new java.io.File(filename).delete
	}
}
