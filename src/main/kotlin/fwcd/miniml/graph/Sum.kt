package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.ShapeMismatchException

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
		val left = operands[0].cachedForward() ?: throw MissingCachedInputArray("Sum")
		
		if (!left.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of summand", left.shape, gradient.shape)
		}
		
		operands[0].backward(gradient)
		operands[1].backward(gradient)
	}
}
