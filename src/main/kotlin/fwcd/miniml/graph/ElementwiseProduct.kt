package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * The elementwise product of two values.
 */
class ElementwiseProduct(
	private val lhs: ValueNode,
	private val rhs: ValueNode
) : ValueNode {
	override fun forward(feedDict: Map<ValueNode, NDArray>) = lhs.forward(feedDict) * rhs.forward(feedDict)
}
