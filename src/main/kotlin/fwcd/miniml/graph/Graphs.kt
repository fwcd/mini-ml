package fwcd.miniml.graph

import fwcd.miniml.math.NDArray

/** Creates a constant node with a gradient. */
fun constant(value: NDArray, name: String? = null) = GradientNode(Constant(value, name))

/** Creates a variable node with a gradient. */
fun variable(value: NDArray, name: String? = null) = GradientNode(Variable(value, name))

/** Creates a placeholder node with a gradient. */
fun placeholder(value: NDArray, name: String? = null) = GradientNode(Placeholder(value, name))
