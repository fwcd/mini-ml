package fwcd.miniml.graph

class ElementwiseSquare(value: ValueNode) : ElementwiseProduct(value, value) {
	override fun toString(): String = "${operands[0]}^2"
}
