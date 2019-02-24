package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.sigmoid
import fwcd.miniml.math.sigmoidDerivative
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

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
