package common

import java.security.MessageDigest

object MD5Hash extends HashFunction {
  def apply(input: Array[Byte]): Array[Byte]= {
    val md5 = MessageDigest.getInstance("MD5")
    md5.reset()
    md5.update(input)
    md5.digest()
  }
}
