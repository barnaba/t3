package t3
import java.io.File;
import common.Chain

class ChainWriter {

  val file = File.createTempFile("ttt",".part")
  val filename = file.getAbsoluteFile

  def write(chains: Array[Chain]) = {
    printToFile(file) { 
      stream => 
      // TODO: Wypisac naglowki, co najmniej dlugosc lancucha, hasla i alfabet - beda potrzebne przy wyszukiwaniu
      chains.map( c => stream.write(c.compact) ) 
    }
  }


  private def printToFile(f: java.io.File)(op: java.io.BufferedOutputStream => Unit) {
    val fos = new java.io.FileOutputStream(file)
    val p = new java.io.BufferedOutputStream(fos)
    try { op(p) } finally { p.close(); fos.close() }
  }

}
