package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * The elementwise multiplicative inverse of a value.
 */
class Reciprocal(
	private val value: ValueNode
) : ValueNode {
	override fun forward(feedDict: Map<ValueNode, NDArray>) = value.forward(feedDict).reciprocal()
}
