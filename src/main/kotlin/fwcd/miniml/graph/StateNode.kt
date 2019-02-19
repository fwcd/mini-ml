package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * A StateNode wraps an (immutable) ValueNode
 * and stores gradients.
 */
class StateNode(private val delegate: ValueNode) : ValueNode {
	var gradient: NDArray? = null
	
	override fun forward() = delegate.forward()
	
	override fun backward(gradient: NDArray) {
		this.gradient = gradient
		delegate.backward(gradient)
	}
}
