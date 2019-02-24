package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<Sum>()

/**
 * The elementwise sum over two values.
 */
class Sum(
	lhs: ValueNode,
	rhs: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(lhs, rhs)
	
	override fun forward() = operands[0].forward() + operands[1].forward()
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through sum: {}", this)
		val left = operands[0].cachedForward() ?: throw MissingCachedInputArrayException("Sum")
		
		if (!left.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of summand", left.shape, gradient.shape)
		}
		
		operands[0].backward(gradient)
		operands[1].backward(gradient)
	}
	
	override fun toString(): String = "(${operands[0]} + ${operands[1]})"
}
