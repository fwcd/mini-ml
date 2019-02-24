package fwcd.miniml.graph

import fwcd.miniml.math.tanhDerivative

/**
 * The tanh function.
 */
class Tanh(
	value: ValueNode
) : UnaryElementwise(
	value,
	"tanh",
	Math::tanh,
	::tanhDerivative
)
