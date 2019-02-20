package fwcd.miniml.graph

class MissingCachedInputArray(nodeType: String) : RuntimeException(
	"Missing cached input array in node of type $nodeType"
)
