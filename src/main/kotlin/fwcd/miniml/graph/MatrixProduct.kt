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
	
	override fun backward(rawGradient: NDArray) {
		val left = operands[0].cachedForward() ?: throw MissingCachedInputArrayException("Matrix product")
		var right = operands[1].cachedForward() ?: throw MissingCachedInputArrayException("Matrix product")
		val gradient: NDArray
		
		if (left.rank != 2) {
			throw ShapeMismatchException("Left factor of the matrix product ($left) should be a matrix")
		} else if (right.rank == 1) {
			// Handle the special case of matrix-vector multiplication
			right = right.asColumnMatrix()
			gradient = rawGradient.asColumnMatrix()
		} else if (right.rank != 2) {
			throw ShapeMismatchException("Right factor of the matrix product ($right) should be a matrix")
		} else {
			gradient = rawGradient
		}
		
		val expectedShape = intArrayOf(left.shape[0], right.shape[1])
		
		if (!gradient.shape.contentEquals(expectedShape)) {
			throw ShapeMismatchException("Gradient of matrix product", expectedShape, gradient.shape)
		}
		
		// Source: https://github.com/tensorflow/tensorflow/blob/9d508106b32eb6518912501d29a80ff9967dfe05/tensorflow/core/ops/math_grad.cc#L813-L827
		
		val leftGradient = when {
			!transposeLeft && !transposeRight -> gradient matmul right.transpose()
			!transposeLeft && transposeRight -> gradient matmul right
			transposeLeft && !transposeRight -> right matmul gradient.transpose()
			else /* transpose both */ -> right.transpose() matmul gradient.transpose()
		}
		val rightGradient = when {
			!transposeLeft && !transposeRight -> left.transpose() matmul gradient
			!transposeLeft && transposeRight -> gradient.transpose() matmul left
			transposeLeft && !transposeRight -> left matmul gradient
			else /* transpose both */ -> gradient.transpose() matmul left.transpose()
		}
		
		operands[0].backward(leftGradient)
		operands[1].backward(rightGradient)
	}
}
