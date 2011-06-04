package common

trait ReduxFunction {
	def redux(hash: Hash): Key
	def setLevel(level: Int): Unit
}
