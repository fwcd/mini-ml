package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.scalarOf
import fwcd.miniml.math.ones
import fwcd.miniml.math.ShapeMismatchException

/**
 * The sum across all elements.
 */
class ReduceSum(
	value: ValueNode
) : ValueNode {
	override val operands: List<ValueNode> = listOf(value)
	
	override fun forward() = scalarOf(operands[0].forward().reduceSum())
	
	override fun backward(gradient: NDArray) {
		if (gradient.rank != 0) {
			throw ShapeMismatchException("While backpropagating", 0, gradient.rank)
		}
		operands[0].cachedForward()?.shape?.let(::ones)?.let(operands[0]::backward)
			?: throw MissingCachedInputArray("ReduceSum")
	}
}
