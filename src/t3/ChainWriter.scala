package t3
import common.Chain

class ChainWriter {

  val writer = new TempWriter()
  val filename = writer.filename

  def write(chains: Array[Chain]) = {
    writer.write() { 
      stream => chains.map( c => stream.write(c.compact) ) 
    }
  }


}
