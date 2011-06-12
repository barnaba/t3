package common

import java.security.MessageDigest

object MD5Hash extends HashFunction {
    val md5 = MessageDigest.getInstance("MD5")

  def apply(input: Array[Byte]): Array[Byte]= {
    org.apache.commons.codec.digest.DigestUtils.md5(input)
  }
}
