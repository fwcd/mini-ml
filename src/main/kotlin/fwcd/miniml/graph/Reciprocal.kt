package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * The elementwise multiplicative inverse of a value.
 */
class Reciprocal(
	value: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(value)
	
	override fun forward() = operands[0].forward().reciprocal()
}
