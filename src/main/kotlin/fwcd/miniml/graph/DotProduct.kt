package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.scalarOf

/**
 * The result of a dot product.
 */
class DotProduct(
	lhs: ValueNode,
	rhs: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(lhs, rhs)
	
	override fun forward() = scalarOf(operands[0].forward().dot(operands[1].forward()))
	
	override fun backward(gradient: NDArray) {
		TODO("Dot product backprop not implemented")
	}
}
