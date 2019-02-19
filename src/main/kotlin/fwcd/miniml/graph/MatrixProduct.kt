/**
 * The result of a matrix multiplication.
 */
class MatrixProduct(
	private val lhs: ValueNode,
	private val rhs: ValueNode
) : ValueNode {
	override fun forward(feedDict: Map<ValueNode, NDArray>) = lhs.forward(feedDict).matmul(rhs.forward(feedDict))
}
