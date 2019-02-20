package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * A variable that is optimized through gradients.
 */
class Variable(
	private val value: NDArray
) : ValueNode {
	override fun forward() = value
	
	override fun apply(delta: NDArray) {
		value += delta
	}
}
