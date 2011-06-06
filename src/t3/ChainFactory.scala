package t3
import common.Chain

class ChainFactory(r: KeyRange) {
  val chains = r.map(key => new Chain(key.getBytes)).toArray
  scala.util.Sorting.quickSort(chains)
  
  val writer = new ChainWriter()
  writer.write(chains)

  val filename = writer.filename
}
