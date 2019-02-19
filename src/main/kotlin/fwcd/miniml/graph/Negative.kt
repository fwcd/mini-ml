package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * The elementwise additive inverse of a value.
 */
class Negative(
	private val value: ValueNode
) : ValueNode {
	override fun forward() = -value.forward()
}
