package fwcd.miniml.graph

class MissingCachedInputArrayException(nodeType: String) : RuntimeException(
	"Missing cached input array in node of type $nodeType"
)
