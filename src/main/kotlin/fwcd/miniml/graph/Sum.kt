package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * The elementwise sum over two values.
 */
class Sum(
	private val lhs: ValueNode,
	private val rhs: ValueNode
) : ValueNode {
	override fun forward(feedDict: Map<ValueNode, NDArray>) = lhs.forward(feedDict) + rhs.forward(feedDict)
}
