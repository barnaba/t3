package t3

import scala.actors.Actor
import scala.collection.mutable.Queue
import java.io.File

case class MergeRequest(filename1: String, filename2: String)
case class ChainRequest(range: KeyRange)

class Manager(workers: Seq[Worker], range: KeyRange, outputFile: String) extends Actor {
	val fileQueue = new Queue[String]
	
	def act() {
		workers.first ! ChainRequest(range)
		
		receive {
			case ChainResult(filename: String) =>
				(new File(filename)).renameTo(new File(outputFile))
		}
		
		exit()
	}
}
