package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<Negative>()

/**
 * The elementwise additive inverse of a value.
 */
class Negative(
	value: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(value)
	
	override fun forward() = -operands[0].forward()
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through negative: {}", this)
		val input = operands[0].cachedForward() ?: throw MissingCachedInputArrayException("Negative")
		
		if (!input.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of negative", input.shape, gradient.shape)
		}
		
		operands[0].backward(-gradient)
	}
	
	override fun toString(): String = "-${operands[0]}"
}
