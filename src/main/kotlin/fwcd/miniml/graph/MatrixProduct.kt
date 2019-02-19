package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * The result of a matrix multiplication.
 */
class MatrixProduct(
	private val lhs: ValueNode,
	private val rhs: ValueNode
) : ValueNode {
	override fun forward() = lhs.forward().matmul(rhs.forward())
}
