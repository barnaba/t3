package t3

import scala.io.Source

object FileMerger {
	def merge(fileName1: String, fileName2: String): String = {
		val cr1 = new ChainReader(fileName1)
		val cr2 = new ChainReader(fileName2)
		
		val writer = new TempWriter

		writer.write() { stream =>
			while(cr1.value != null && cr2.value != null)
				stream.write((cr1.popWhereMin(cr2) + "\n").getBytes)
			
			if(cr1.value != null)
				stream.write((cr1.value + "\n").getBytes)
			for(line <- cr1.lines)
				stream.write((line + "\n").getBytes)
			
			if(cr2.value != null)
				stream.write((cr2.value + "\n").getBytes)
			for(line <- cr2.lines)
				stream.write((line + "\n").getBytes)
		}
		
		writer.filename
	}
}
