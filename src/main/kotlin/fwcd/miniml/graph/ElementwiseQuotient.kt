package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * The elementwise quotient of two values.
 */
class ElementwiseQuotient(
	private val lhs: ValueNode,
	private val rhs: ValueNode
) : ValueNode {
	override fun forward(feedDict: Map<ValueNode, NDArray>) = lhs.forward(feedDict) / rhs.forward(feedDict)
}
