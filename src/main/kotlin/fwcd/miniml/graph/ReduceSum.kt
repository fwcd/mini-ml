package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.scalarOf
import fwcd.miniml.math.ones
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<ReduceSum>()

/**
 * The sum across all elements.
 */
class ReduceSum(
	value: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(value)
	
	override fun forward() = scalarOf(operands[0].forward().reduceSum())
	
	override fun backward(gradient: NDArray) {
		LOG.debug("Backpropagating through reduce sum: {}", this)
		if (gradient.rank != 0) {
			throw ShapeMismatchException("Gradient of reduce sum", 0, gradient.rank)
		}
		
		operands[0].cachedForward()?.shape
			?.let(::ones)
			?.let { it * gradient[0] }
			?.let(operands[0]::backward)
			?: throw MissingCachedInputArrayException("ReduceSum")
	}
	
	override fun toString(): String = "(Sum over [${operands[0]}])"
}
