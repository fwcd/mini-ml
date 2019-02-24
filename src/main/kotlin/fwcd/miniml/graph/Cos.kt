package fwcd.miniml.graph

/**
 * The trigonometric cosine function.
 */
class Cos(
	value: ValueNode
) : UnaryElementwise(
	value,
	"cos",
	Math::cos,
	{ -Math.sin(it) }
)
