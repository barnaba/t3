package common

trait ReduxFunction{
	def apply(input: Array[Byte], level: Int):Array[Byte]
}
