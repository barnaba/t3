package t3

class TempWriter {

  val file = java.io.File.createTempFile("ttt",".part")
  val filename = file.getAbsoluteFile.toString

  def write()(op: java.io.BufferedOutputStream => Unit) {
    val fos = new java.io.FileOutputStream(file)
    val p = new java.io.BufferedOutputStream(fos)
    try { op(p) } finally { p.close(); fos.close() }
  }

}
