package fwcd.miniml.graph

/**
 * The exponential function (e to the power of x).
 */
class Exp(
	value: ValueNode
) : UnaryElementwise(
	value,
	"exp",
	Math::exp,
	Math::exp
)
