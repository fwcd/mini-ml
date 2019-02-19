package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/**
 * An operation that outputs an n-dimensional array.
 */
interface ValueNode {
	fun forward(feedDict: Map<ValueNode, NDArray>): NDArray
}
