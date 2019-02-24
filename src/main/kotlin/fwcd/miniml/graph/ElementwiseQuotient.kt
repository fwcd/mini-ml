package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<ElementwiseQuotient>()

/**
 * The elementwise quotient of two values.
 */
class ElementwiseQuotient(
	lhs: ValueNode,
	rhs: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(lhs, rhs)
	
	override fun forward() = operands[0].forward() / operands[1].forward()
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through elementwise quotient: {}", this)
		val left = operands[0].cachedForward() ?: throw MissingCachedInputArrayException("Elementwise quotient")
		val right = operands[1].cachedForward() ?: throw MissingCachedInputArrayException("Elementwise quotient")
		
		if (!left.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of elementwise quotient", left.shape, gradient.shape)
		}
		
		operands[0].backward(gradient / right)
		operands[1].backward(-(left * gradient) / (right * right))
	}
	
	override fun toString(): String = "(${operands[0]} / ${operands[1]})"
}
