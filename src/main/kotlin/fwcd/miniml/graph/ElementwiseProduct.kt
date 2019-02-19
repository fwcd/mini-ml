package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * The elementwise product of two values.
 */
class ElementwiseProduct(
	private val lhs: ValueNode,
	private val rhs: ValueNode
) : ValueNode {
	override fun forward() = lhs.forward() * rhs.forward()
}
