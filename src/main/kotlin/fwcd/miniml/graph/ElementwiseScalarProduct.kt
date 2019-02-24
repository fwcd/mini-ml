package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.scalarOf
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<ElementwiseScalarProduct>()

/**
 * The elementwise product with a scalar.
 */
open class ElementwiseScalarProduct(
	lhs: ValueNode,
	rhs: Double
) : ValueNode {
	override val operands: List<ValueNode> = listOf(lhs, Constant(scalarOf(rhs)))
	
	override fun forward() = operands[0].forward() * operands[1].forward()
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through elementwise scalar product: {}", this)
		val left = operands[0].cachedForward() ?: throw MissingCachedInputArrayException("Elementwise scalar product")
		val right = (operands[1] as Constant).value
		
		if (!left.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of elementwise scalar product", left.shape, gradient.shape)
		}
		
		operands[0].backward(gradient * right.expectScalar())
	}
	
	override fun toString(): String = "(${operands[0]} * ${operands[1]})"
}
