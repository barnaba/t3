package t3

import scala.io.Source
import java.io.BufferedOutputStream

class FileMerger(filename1: String, filename2: String) {
	val readers = Seq(filename1, filename2) map { new ChainReader(_) }
	val writer = new TempWriter
	
	writer.write() { stream =>
		while(readers(0).hasNext && readers(1).hasNext)
			writeLine(stream, readers(0).popMinimal(readers(1)))
		
		readers foreach { reader =>
			if(reader.hasNext) writeLine(stream, reader.pop)
		}
	}
	
	readers foreach { _.close }
	
	def writeLine(stream: BufferedOutputStream, line: String) {
		stream.write((line + "\n").getBytes)
	}
	
	def filename = writer.filename
}
