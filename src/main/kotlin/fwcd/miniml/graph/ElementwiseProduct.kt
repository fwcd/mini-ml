package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.ShapeMismatchException

/**
 * The elementwise product of two values.
 */
open class ElementwiseProduct(
	lhs: ValueNode,
	rhs: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(lhs, rhs)
	
	override fun forward() = operands[0].forward() * operands[1].forward()
	
	override fun backward(gradient: NDArray) {
		val left = operands[0].cachedForward() ?: throw MissingCachedInputArrayException("Elementwise product")
		val right = operands[1].cachedForward() ?: throw MissingCachedInputArrayException("Elementwise product")
		
		if (!left.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of elementwise product", left.shape, gradient.shape)
		}
		
		operands[0].backward(right * gradient)
		operands[1].backward(left * gradient)
	}
}
