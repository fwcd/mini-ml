package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * The elementwise difference between two values.
 */
class Difference(
	lhs: ValueNode,
	rhs: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(lhs, rhs)
	
	override fun forward() = operands[0].forward() - operands[1].forward()
}
