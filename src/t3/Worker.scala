package t3

import scala.actors.Actor

case class MergeResult(filename: String)
case class ChainResult(filename: String)

class Worker extends Actor {
	def act() {
		loop {
			react {
				case MergeRequest(filename1: String, filename2: String) => {
					val merger = new FileMerger(filename1, filename2)
					sender ! MergeResult(merger.filename)
				}
				case ChainRequest(range: KeyRange) => {
				    val factory = new ChainFactory(range)
				    sender ! ChainResult(factory.filename)
				}
			}
		}
	}
}
