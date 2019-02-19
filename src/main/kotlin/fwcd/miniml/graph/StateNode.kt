package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * A StateNode wraps an (immutable) ValueNode
 * and stores gradients. Additionally, they
 * provide convenient overloads of common operations
 * to simplify construction of computation graphs.
 */
class StateNode(private val delegate: ValueNode) : ValueNode {
	var gradient: NDArray? = null
	
	override fun forward(): NDArray = delegate.forward()
	
	override fun backward(gradient: NDArray) {
		this.gradient = gradient
		delegate.backward(gradient)
	}
	
	operator fun plus(rhs: ValueNode): StateNode = StateNode(Sum(this, rhs))
	
	operator fun minus(rhs: ValueNode): StateNode = StateNode(Difference(this, rhs))
	
	operator fun times(rhs: ValueNode): StateNode = StateNode(ElementwiseProduct(this, rhs))
	
	operator fun div(rhs: ValueNode): StateNode = StateNode(ElementwiseQuotient(this, rhs))
	
	operator fun unaryPlus(): StateNode = this
	
	operator fun unaryMinus(): StateNode = StateNode(Negative(this))
	
	fun reciprocal(): StateNode = StateNode(Reciprocal(this))
	
	fun dot(rhs: ValueNode): StateNode = StateNode(DotProduct(this, rhs))
	
	fun matmul(rhs: ValueNode): StateNode = StateNode(MatrixProduct(this, rhs))
}
