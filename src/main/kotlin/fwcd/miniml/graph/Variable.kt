package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<Variable>()

/**
 * A variable that is optimized through gradients.
 */
class Variable(
	initialValue: NDArray,
	private val name: String? = null
) : ValueNode {
	private val value = initialValue.copy(mutableCopy = true)
	override val operands: List<ValueNode> = emptyList()
	
	override fun forward() = value
	
	override fun apply(delta: NDArray) {
		LOG.debug("Applying gradient {} to {} - stored value: {}", delta, name ?: "variable", value)
		value += delta
	}
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through variable: {} {} - gradient: {}", name ?: "var", value, gradient)
	}
	
	override fun toString(): String = name ?: "(var $value)"
}
