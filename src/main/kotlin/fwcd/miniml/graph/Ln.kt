package fwcd.miniml.graph

/**
 * The natural logarithm.
 */
class Ln(
	value: ValueNode
) : UnaryElementwise(
	value,
	"ln",
	Math::log,
	{ 1.0 / it }
)
