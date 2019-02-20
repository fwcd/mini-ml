package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * The elementwise sum over two values.
 */
class Sum(
	lhs: ValueNode,
	rhs: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(lhs, rhs)
	
	override fun forward() = operands[0].forward() + operands[1].forward()
}
