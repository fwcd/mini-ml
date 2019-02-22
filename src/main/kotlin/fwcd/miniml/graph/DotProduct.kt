package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.scalarOf
import fwcd.miniml.math.ShapeMismatchException

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
		val left = operands[0].cachedForward() ?: throw MissingCachedInputArray("Dot product")
		val right = operands[1].cachedForward() ?: throw MissingCachedInputArray("Dot product")
		
		if (!left.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of dot product", left.shape, gradient.shape)
		}
		
		operands[0].backward(right * gradient)
		operands[1].backward(left * gradient)
	}
}
