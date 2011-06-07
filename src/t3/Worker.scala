package t3

import scala.actors.Actor

case class MergeResult(fileName: String)
case class ChainResult(fileName: String)

class Worker extends Actor {
	def act() {
		loop {
			react {
				case MergeRequest(fileName1: String, fileName2: String) => {
					val fileName = FileMerger.merge(fileName1, fileName2)
					sender ! MergeResult(fileName)
				}
				case ChainRequest(range: KeyRange) => {
				    val factory = new ChainFactory(range)
				    sender ! ChainResult(factory.filename)
				}
			}
		}
	}
}
