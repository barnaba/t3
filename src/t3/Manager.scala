package t3

import scala.actors.Actor
import scala.collection.mutable.Queue
import java.io.File

case class MergeRequest(fileName1: String, fileName2: String)
case class ChainRequest(range: KeyRange)

class Manager(workers: Seq[Worker], range: KeyRange, outputFile: String) extends Actor {
	val fileQueue = new Queue[String]
	
	def act() {
		workers.first ! ChainRequest(range)
		
		receive {
			case ChainResult(fileName: String) =>
				(new File(fileName)).renameTo(new File(outputFile))
		}
		
		exit()
	}
}
