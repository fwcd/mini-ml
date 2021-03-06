package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<Difference>()

/**
 * The elementwise difference between two values.
 */
class Difference(
	lhs: ValueNode,
	rhs: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(lhs, rhs)
	
	override fun forward() = operands[0].forward() - operands[1].forward()
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through difference: {}", this)
		val left = operands[0].cachedForward() ?: throw MissingCachedInputArrayException("Difference")
		
		if (!left.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of minuend", left.shape, gradient.shape)
		}
		
		operands[0].backward(gradient)
		operands[1].backward(-gradient)
	}
	
	override fun toString(): String = "(${operands[0]} - ${operands[1]})"
}
