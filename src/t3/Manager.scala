package t3

import scala.actors.Actor
import scala.collection.mutable.Queue
import java.io.File

case class MergeRequest(filename1: String, filename2: String)
case class ChainRequest(range: KeyRange)
case class Stop 

class Manager(workers: Seq[Worker], range: KeyRange, outputFile: String)
		extends Actor
{
	val workerQueue = new Queue[Worker]
  workers.foreach {workerQueue.enqueue(_)}
	val fileQueue = new Queue[String]
	
	var workDone = false
	
  def act() {
    while(!workDone){
      workerQueue dequeueAll { _ => true } foreach { request(_) }

      if(workerQueue.length == workers.length){
        // no request was successful, probably we are out of work
        saveFile(fileQueue.dequeue)
        workDone = true
      } else {
        receive {
          case Result(filename: String) =>
          fileQueue.enqueue(filename)
          workerQueue.enqueue(sender.asInstanceOf[Worker])
        }
      }
    }
  }

	def request(worker: Worker) {
		if(range.hasNext) {
			worker ! ChainRequest(range.pop(4096))
		}
		else {
			if(fileQueue.length >= 2) {
				worker ! MergeRequest(fileQueue.dequeue, fileQueue.dequeue)
			} else {
				workerQueue.enqueue(worker)
			}
		}
	}
	
	def saveFile(filename: String) {
		(new File(filename)).renameTo(new File(outputFile))
	}
}
