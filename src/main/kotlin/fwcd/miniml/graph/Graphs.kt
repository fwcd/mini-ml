package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/** Creates a constant node with a gradient. */
fun constant(value: NDArray) = GradientNode(Constant(value))

/** Creates a variable node with a gradient. */
fun variable(value: NDArray) = GradientNode(Variable(value))

/** Creates a placeholder node with a gradient. */
fun placeholder(value: NDArray) = GradientNode(Placeholder(value))
