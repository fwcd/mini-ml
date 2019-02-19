/**
 * The elementwise difference between two values.
 */
class Difference(
	private val lhs: ValueNode,
	private val rhs: ValueNode
) : ValueNode {
	override fun forward(feedDict: Map<ValueNode, NDArray>) = lhs.forward(feedDict) - rhs.forward(feedDict)
}
