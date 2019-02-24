package fwcd.miniml.graph

/**
 * The trigonometric sine function.
 */
class Sin(
	value: ValueNode
) : UnaryElementwise(
	value,
	"sin",
	Math::sin,
	Math::cos
)
