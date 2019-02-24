package fwcd.miniml.graph

/**
 * The elementwise additive inverse of a value.
 */
class Negative(
	value: ValueNode
) : UnaryElementwise(
	value,
	"negative",
	{ -it },
	{ -1.0 }
)
