package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<Placeholder>()

/**
 * A variable that is not optimized through gradients.
 * Usually, these are inputs to the computation graph.
 */
class Placeholder(
	var value: NDArray,
	private val name: String? = null
) : ValueNode {
	override val operands: List<ValueNode> = emptyList()
	
	override fun forward() = value
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through placeholder: {} {}", name ?: "ph", value)
	}
	
	override fun toString(): String = name ?: "(ph $value)"
}
