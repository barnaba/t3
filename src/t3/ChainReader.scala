package t3

import scala.io.Source

class ChainReader(fileName: String) {
	val lines = Source.fromFile(fileName).getLines
	var value: String = lines.next
	
	def pop(): String = {
		val result = value
		value = if(lines.hasNext) lines.next else null
		result
	}
	
	def popWhereMin(other: ChainReader): String = {
		if(this.value < other.value) this.pop() else other.pop()
	}
}
