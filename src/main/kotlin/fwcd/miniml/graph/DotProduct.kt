/**
 * The result of a dot product.
 */
class DotProduct(
	private val lhs: ValueNode,
	private val rhs: ValueNode
) : ValueNode {
	override fun forward(feedDict: Map<ValueNode, NDArray>) = lhs.forward(feedDict).dot(rhs.forward(feedDict))
}
