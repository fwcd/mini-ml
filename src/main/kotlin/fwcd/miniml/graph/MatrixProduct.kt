package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * The result of a matrix multiplication.
 */
class MatrixProduct(
	lhs: ValueNode,
	rhs: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(lhs, rhs)
	
	override fun forward() = operands[0].forward().matmul(operands[1].forward())
	
	override fun backward(gradient: NDArray) {
		TODO("Matmul backprop not implemented")
	}
}
