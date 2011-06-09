package t3

import scala.actors.Actor

case class Result(filename: String)

class Worker extends Actor {
	def act() {
		loop {
			react {
				case MergeRequest(filename1: String, filename2: String) => {
					val merger = new FileMerger(filename1, filename2)
					sender ! Result(merger.filename)
				}
				case ChainRequest(range: KeyRange) => {
				    val factory = new ChainFactory(range)
				    sender ! Result(factory.filename)
				}
			}
		}
	}
}
