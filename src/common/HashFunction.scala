package common

trait HashFunction {
	def apply(input : Array[Byte]): Array[Byte]
}
