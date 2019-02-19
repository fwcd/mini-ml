interface ValueNode {
	fun forward(feedDict: Map<ValueNode, NDArray>): NDArray?
}
