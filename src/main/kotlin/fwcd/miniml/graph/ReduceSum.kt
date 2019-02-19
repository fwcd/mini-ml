/**
 * The sum across all elements.
 */
class ReduceSum(
	private val value: ValueNode
) : ValueNode {
	override fun forward(feedDict: Map<ValueNode, NDArray>) = scalarOf(value.forward(feedDict).reduceSum())
}
