package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<Reciprocal>()

/**
 * The elementwise multiplicative inverse of a value.
 */
class Reciprocal(
	value: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(value)
	
	override fun forward() = operands[0].forward().reciprocal()
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through reciprocal: {}", this)
		val input = operands[0].cachedForward() ?: throw MissingCachedInputArrayException("Reciprocal")
		
		if (!input.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of reciprocal", input.shape, gradient.shape)
		}
		
		operands[0].backward(-(input * input).reciprocal() * gradient)
	}
	
	override fun toString(): String = "(1 / ${operands[0]})"
}
