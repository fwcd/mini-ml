package fwcd.miniml.graph

import fwcd.miniml.math.tanDerivative

/**
 * The trigonometric tangent function.
 */
class Tan(
	value: ValueNode
) : UnaryElementwise(
	value,
	"tan",
	Math::tan,
	::tanDerivative
)
