package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<Constant>()

/**
 * A constant value.
 */
class Constant(
	private val value: NDArray,
	private val name: String? = null
) : ValueNode {
	override val operands: List<ValueNode> = emptyList()
	
	override fun forward() = value
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through constant: {} {}", name ?: "const", value)
	}
	
	override fun toString(): String = name ?: "(const $value)"
}
