package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<Negative>()

/**
 * The elementwise additive inverse of a value.
 */
class Negative(
	value: ValueNode
) : UnaryElementwise(
	value,
	"negative",
	{ -it },
	{ -1.0 }
)
