/**
 * The elementwise additive inverse of a value.
 */
class Negative(
	private val value: ValueNode
) : ValueNode {
	override fun forward(feedDict: Map<ValueNode, NDArray>) = -value.forward(feedDict)
}
