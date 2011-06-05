package common

trait HashFunction {
	def hash(input : Array[Byte]): Array[Byte]
}
