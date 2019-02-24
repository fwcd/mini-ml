package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<GradientNode>()

/**
 * A GradientNode wraps an (immutable) ValueNode
 * and stores gradients. Additionally, they
 * provide convenient overloads of common operations
 * to simplify construction of computation graphs.
 */
class GradientNode(private val delegate: ValueNode) : ValueNode {
	private var cached: NDArray? = null
	var gradient: NDArray? = null
	var value: NDArray?
		get() = (delegate as? Placeholder)?.value
		set(newValue: NDArray?) = (delegate as? Placeholder)
			?.let { it.value = newValue ?: throw IllegalArgumentException("Null should not be assigned to GradientNode.value") }
			?: Unit
	override val operands: List<ValueNode>
		get() = delegate.operands
	
	override fun forward(): NDArray = delegate.forward().also {
		cached = it
	}
	
	/** Recursively clears gradients. */
	fun clearGradients() {
		gradient = null
		for (parent in delegate.operands) {
			(parent as? GradientNode)?.clearGradients()
		}
	}
	
	override fun cachedForward(): NDArray? = cached
	
	override fun backward(gradient: NDArray) {
		LOG.trace("Backpropagating through GradientNode: {} - stored gradient: {} - propagated gradient: {}", this, this.gradient, gradient)
		if (cached == null) {
			throw IllegalStateException("Calling .backward() without a forwardpass is not permitted. Try inserting a .forward() call before.")
		}
		
		// Accumulate gradient
		this.gradient
			?.also { it += gradient }
			?: run { this.gradient = gradient.copy(mutableCopy = true) }
		
		delegate.backward(gradient)
	}
	
	operator fun plus(rhs: ValueNode): GradientNode = GradientNode(Sum(this, rhs))
	
	operator fun minus(rhs: ValueNode): GradientNode = GradientNode(Difference(this, rhs))
	
	operator fun times(rhs: ValueNode): GradientNode = GradientNode(ElementwiseProduct(this, rhs))
	
	operator fun div(rhs: ValueNode): GradientNode = GradientNode(ElementwiseQuotient(this, rhs))
	
	operator fun unaryPlus(): GradientNode = this
	
	operator fun unaryMinus(): GradientNode = GradientNode(Negative(this))
	
	fun reciprocal(): GradientNode = GradientNode(Reciprocal(this))
	
	infix fun dot(rhs: ValueNode): GradientNode = GradientNode(DotProduct(this, rhs))
	
	infix fun matmul(rhs: ValueNode): GradientNode = GradientNode(MatrixProduct(this, rhs))
	
	fun square(): GradientNode = GradientNode(ElementwiseSquare(this))
	
	fun reduceSum(): GradientNode = GradientNode(ReduceSum(this))
	
	override fun toString(): String = delegate.toString()
}
