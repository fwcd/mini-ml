package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<Variable>()

/**
 * A variable that is optimized through gradients.
 */
class Variable(
	private val value: NDArray
) : ValueNode {
	override val operands: List<ValueNode> = emptyList()
	
	override fun forward() = value
	
	override fun apply(delta: NDArray) {
		value += delta
	}
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through variable: {}", value)
	}
	
	override fun toString(): String = "(var $value)"
}
