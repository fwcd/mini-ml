package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<Variable>()

/**
 * A variable that is optimized through gradients.
 */
class Variable(
	private val value: NDArray,
	private val name: String? = null
) : ValueNode {
	override val operands: List<ValueNode> = emptyList()
	
	override fun forward() = value
	
	override fun apply(delta: NDArray) {
		value += delta
	}
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through variable: {} {}", name ?: "var", value)
	}
	
	override fun toString(): String = name ?: "(var $value)"
}
