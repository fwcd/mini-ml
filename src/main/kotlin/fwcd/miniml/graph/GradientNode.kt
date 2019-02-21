package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * A GradientNode wraps an (immutable) ValueNode
 * and stores gradients. Additionally, they
 * provide convenient overloads of common operations
 * to simplify construction of computation graphs.
 */
class GradientNode(private val delegate: ValueNode) : ValueNode {
	private var cached: NDArray? = null
	var gradient: NDArray? = null
	override val operands: List<ValueNode>
		get() = delegate.operands
	
	override fun forward(): NDArray = delegate.forward().also {
		cached = it
	}
	
	override fun cachedForward(): NDArray? = cached
	
	override fun backward(gradient: NDArray) {
		this.gradient?.also { it += gradient } ?: run {
			this.gradient = gradient
		}
		delegate.backward(gradient)
	}
	
	operator fun plus(rhs: ValueNode): GradientNode = GradientNode(Sum(this, rhs))
	
	operator fun minus(rhs: ValueNode): GradientNode = GradientNode(Difference(this, rhs))
	
	operator fun times(rhs: ValueNode): GradientNode = GradientNode(ElementwiseProduct(this, rhs))
	
	operator fun div(rhs: ValueNode): GradientNode = GradientNode(ElementwiseQuotient(this, rhs))
	
	operator fun unaryPlus(): GradientNode = this
	
	operator fun unaryMinus(): GradientNode = GradientNode(Negative(this))
	
	fun reciprocal(): GradientNode = GradientNode(Reciprocal(this))
	
	fun dot(rhs: ValueNode): GradientNode = GradientNode(DotProduct(this, rhs))
	
	fun matmul(rhs: ValueNode): GradientNode = GradientNode(MatrixProduct(this, rhs))
	
	fun square(): GradientNode = GradientNode(ElementwiseSquare(this))
}
