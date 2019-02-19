package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.scalarOf

/**
 * The result of a dot product.
 */
class DotProduct(
	private val lhs: ValueNode,
	private val rhs: ValueNode
) : ValueNode {
	override fun forward() = scalarOf(lhs.forward().dot(rhs.forward()))
}
