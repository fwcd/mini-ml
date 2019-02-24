package fwcd.miniml.graph

/**
 * The elementwise multiplicative inverse of a value.
 */
class Reciprocal(
	value: ValueNode
) : UnaryElementwise(
	value,
	"reciprocal",
	{ 1.0 / it },
	{ -1.0 / (it * it) }
)
