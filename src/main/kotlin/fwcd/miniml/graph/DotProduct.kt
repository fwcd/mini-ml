package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.scalarOf
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<DotProduct>()

/**
 * The result of a dot product.
 */
class DotProduct(
	lhs: ValueNode,
	rhs: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(lhs, rhs)
	
	override fun forward() = scalarOf(operands[0].forward().dot(operands[1].forward()))
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through dot product: {}", this)
		val left = operands[0].cachedForward() ?: throw MissingCachedInputArrayException("Dot product")
		val right = operands[1].cachedForward() ?: throw MissingCachedInputArrayException("Dot product")
		
		operands[0].backward(right * gradient)
		operands[1].backward(left * gradient)
	}
	
	override fun toString(): String = "(${operands[0]} dot ${operands[1]})"
}
