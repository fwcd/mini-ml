package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * The elementwise additive inverse of a value.
 */
class Negative(
	value: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(value)
	
	override fun forward() = -operands[0].forward()
}
