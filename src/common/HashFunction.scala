package common

trait HashFunction {
	def hash(key: Key): Hash
}
