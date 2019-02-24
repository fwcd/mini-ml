package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.sigmoid
import fwcd.miniml.math.sigmoidDerivative
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<Sigmoid>()

/**
 * The sigmoid function.
 */
class Sigmoid(
	value: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(value)
	
	override fun forward() = operands[0].forward().map(::sigmoid)
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through sigmoid: {}", this)
		val value = operands[0].cachedForward() ?: throw MissingCachedInputArrayException("Sigmoid")
		
		if (!value.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of sigmoid", value.shape, gradient.shape)
		}
		
		operands[0].backward(value.map(::sigmoidDerivative) * gradient)
	}
	
	override fun toString(): String = "sigmoid(${operands[0]})"
}
