package fwcd.miniml.graph

/**
 * The natural logarithm.
 */
class NaturalLog(
	value: ValueNode
) : UnaryElementwise(
	value,
	"log",
	Math::log,
	{ 1.0 / it }
)
