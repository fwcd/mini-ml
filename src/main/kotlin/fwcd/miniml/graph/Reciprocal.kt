package fwcd.miniml.graph

import fwcd.miniml.math.NDArray
import fwcd.miniml.math.ShapeMismatchException
import fwcd.miniml.utils.loggerFor

private val LOG = loggerFor<Reciprocal>()

/**
 * The elementwise multiplicative inverse of a value.
 */
class Reciprocal(
	value: ValueNode
) : UnaryElementwise(
	value,
	"reciprocal",
	{ 1.0 / it },
	{ -1.0 / (it * it) }
)
