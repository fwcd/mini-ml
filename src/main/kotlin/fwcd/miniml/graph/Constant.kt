package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * A constant value.
 */
class Constant(
	private val value: NDArray
) : ValueNode {
	override fun forward() = value
}
