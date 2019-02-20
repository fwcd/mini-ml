package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * A variable that is not optimized through gradients.
 * Usually, these are inputs to the computation graph.
 */
class Placeholder(
	private val value: NDArray
) : ValueNode {
	override fun forward() = value
}
