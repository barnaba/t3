package common

trait ReduxFunction{
	def redux(input: Array[Byte], level: Int):Array[Byte]
}
