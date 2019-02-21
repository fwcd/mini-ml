package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.ShapeMismatchException

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
		val left = operands[0].cachedForward() ?: throw MissingCachedInputArray("Elementwise quotient")
		val right = operands[1].cachedForward() ?: throw MissingCachedInputArray("Elementwise quotient")
		
		if (!left.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of elementwise quotient", left.shape, gradient.shape)
		}
		
		operands[0].backward(gradient / right)
		operands[1].backward(-(left * gradient) / (right * right))
	}
}
