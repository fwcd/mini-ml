package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.scalarOf

/**
 * An operation that outputs an n-dimensional array.
 */
interface ValueNode {
	val operands: List<ValueNode>
	
	/** Computes this node's output. */
	fun forward(): NDArray
	
	/** Backpropagates a gradient. */
	fun backward(gradient: NDArray)
	
	/** Returns a cached input result from the last forward() invocation if this node supports caching. */
	fun cachedForward(): NDArray? = null
	
	/** Backpropagates a scalar one as the gradient. */
	fun backward() {
		backward(scalarOf(1.0))
	}
	
	/**
	 * Applies a delta to this node if possible.
	 * Usually this is a processed gradient.
	 */
	fun apply(delta: NDArray) {}
}
