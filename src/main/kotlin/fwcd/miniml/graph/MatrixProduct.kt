package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.ShapeMismatchException

/**
 * The result of a matrix multiplication.
 */
class MatrixProduct(
	lhs: ValueNode,
	rhs: ValueNode,
	private val transposeLeft: Boolean = false,
	private val transposeRight: Boolean = false
) : ValueNode {
	override val operands: List<ValueNode> = listOf(lhs, rhs)
	
	override fun forward(): NDArray {
		val left = operands[0].forward().let { if (transposeLeft) it.transpose() else it }
		val right = operands[1].forward().let { if (transposeRight) it.transpose() else it }
		return left.matmul(right)
	}
	
	override fun backward(gradient: NDArray) {
		val left = operands[0].cachedForward() ?: throw MissingCachedInputArrayException("Matrix product")
		val right = operands[1].cachedForward() ?: throw MissingCachedInputArrayException("Matrix product")
		
		if (!left.shape.contentEquals(gradient.shape)) {
			throw ShapeMismatchException("Gradient of matrix product", left.shape, gradient.shape)
		}
		
		// Source: https://github.com/tensorflow/tensorflow/blob/9d508106b32eb6518912501d29a80ff9967dfe05/tensorflow/core/ops/math_grad.cc#L813-L827
		
		val leftGradient = when {
			!transposeLeft && !transposeRight -> gradient * right.transpose()
			!transposeLeft && transposeRight -> gradient * right
			transposeLeft && !transposeRight -> right * gradient.transpose()
			else /* transpose both */ -> right.transpose() * gradient.transpose()
		}
		val rightGradient = when {
			!transposeLeft && !transposeRight -> left.transpose() * gradient
			!transposeLeft && transposeRight -> gradient.transpose() * left
			transposeLeft && !transposeRight -> left * gradient
			else /* transpose both */ -> gradient.transpose() * left.transpose()
		}
		
		operands[0].backward(leftGradient)
		operands[1].backward(rightGradient)
	}
}
