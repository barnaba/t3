package t3

import common._

object Main {
	private final val workerCount = 4
	
	def main(args: Array[String]) {
		if(args.length < 5) {
			System.out.println("usage: t3 keyLength chainLength step alphabet outputFile")
			return
		}
		
		val keyLength = args(0).toInt
		val chainLength = args(1).toInt
		val step = args(2).toInt
		val alphabet = args(3)
		val outputFile = args(4)
		
		val range = KeyRange(alphabet, keyLength, step);
		
		Chain.redux = new MD5Redux(alphabet, keyLength)
		Chain.hash = MD5Hash
		Chain.length = chainLength
		
		val workers: Seq[Worker] = (1 to workerCount) map { _ => new Worker() }
		val manager = new Manager(workers, range, outputFile)
		
		manager.start
		workers foreach { _.start }
	}
}
