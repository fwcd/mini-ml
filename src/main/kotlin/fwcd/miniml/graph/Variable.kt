package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * A variable. Depending on whether "requiresGradient"
 * is set, gradients will be applied to the stored nd-array.
 */
class Variable(
	private val value: NDArray,
	private val requiresGradient: Boolean = false
) : ValueNode {
	override fun forward() = value
	
	override fun apply(delta: NDArray) {
		value += delta
	}
}
