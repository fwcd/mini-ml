package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * A constant value.
 */
class Constant(
	private val value: NDArray
) : ValueNode {
	override val operands: List<ValueNode> = emptyList()
	
	override fun forward() = value
	
	override fun backward(gradient: NDArray) {
		
	}
}
