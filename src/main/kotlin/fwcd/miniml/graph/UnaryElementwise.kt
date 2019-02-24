package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<UnaryElementwise>()

/**
 * A single-input, elementwise function.
 */
open class UnaryElementwise(
	value: ValueNode,
	private val functionName: String,
	private val function: (Double) -> Double,
	private val derivative: (Double) -> Double
) : ValueNode {
	override val operands: List<ValueNode> = listOf(value)
	
	override fun forward() = operands[0].forward().map(function)
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through {}: {}", functionName, this)
		val value = operands[0].cachedForward() ?: throw MissingCachedInputArrayException(functionName)
		
		if (!value.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of $functionName", value.shape, gradient.shape)
		}
		
		operands[0].backward(value.map(derivative) * gradient)
	}
	
	override fun toString(): String = "$functionName(${operands[0]})"
}
