package fwcd.miniml.graph

import fwcd.miniml.math.sigmoid
import fwcd.miniml.math.sigmoidDerivative

/**
 * The sigmoid function.
 */
class Sigmoid(
	value: ValueNode
) : UnaryElementwise(
	value,
	"sigmoid",
	::sigmoid,
	::sigmoidDerivative
)
