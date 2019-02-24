package fwcd.miniml.math

fun sigmoid(x: Double): Double = 1.0 / (1 + Math.exp(-x))

fun relu(x: Double): Double = Math.max(0.0, x)

fun sigmoidDerivative(x: Double): Double {
	val sig = sigmoid(x)
	return sig * (1.0 - sig)
}

fun reluDerivative(x: Double): Double = if (x >= 0) 1.0 else 0.0
