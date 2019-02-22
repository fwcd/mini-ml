package fwcd.miniml.math

import kotlin.random.Random

/** The scalar one as a constant. */
val ONE = ones().apply { mutable = false }

/** The scalar zero as a constant. */
val ZERO = zeros().apply { mutable = false }

/** Creates an n-dimensional array filled with zeros. */
fun zeros(vararg shape: Int) = NDArray(DoubleArray(toFlattenedSize(shape)), shape)

/** Creates an n-dimensional array filled with ones. */
fun ones(vararg shape: Int) = fill(1.0, *shape)

/** Creates an n-dimensional array filled with a scalar value. */
fun fill(value: Double, vararg shape: Int) = NDArray(DoubleArray(toFlattenedSize(shape)) { value }, shape)

/** Creates an n-dimensional array filled with uniformly distributed random values in the given range. */
fun randoms(low: Double, high: Double, vararg shape: Int) = NDArray(DoubleArray(toFlattenedSize(shape)) { Random.nextDouble(low, high) }, shape)

/** Syntactic sugar for doubleArrayOf. */
fun rowOf(vararg values: Double): DoubleArray = values

/** Syntactic sugar for creating a double array using ints. */
fun rowOfInts(vararg values: Int): DoubleArray {
	val result = DoubleArray(values.size)
	for (i in 0 until values.size) {
		result[i] = values[i].toDouble()
	}
	return result
}

/** Creates a 1-dimensional array of the given ints. */
fun vectorOfInts(vararg values: Int) = NDArray(rowOfInts(*values), intArrayOf(values.size))

/** Creates a 0-dimensional array of the given values. */
fun scalarOf(value: Double) = NDArray(doubleArrayOf(value), intArrayOf())

/** Creates a 0-dimensional array of an integer. */
fun scalarOfInt(value: Int) = scalarOf(value.toDouble())

/** Creates a 1-dimensional array of the given values. */
fun vectorOf(vararg values: Double) = NDArray(values, intArrayOf(values.size))

/** Creates a 2-dimensional array of the given rows. */
fun matrixOf(vararg values: DoubleArray): NDArray {
	val height = values.size
	val width = if (values.isEmpty()) 0 else values[0].size
	var nd = zeros(height, width)
	
	for (y in 0 until height) {
		for (x in 0 until width) {
			nd[y, x] = values[y][x]
		}
	}
	
	return nd
}

/** Creates a 3-dimensional value of the given slices. */
fun matrix3DOf(vararg values: Array<DoubleArray>): NDArray {
	val depth = values.size
	val height = if (values.isEmpty()) 0 else values[0].size
	val width = if (height == 0 || values[0].isEmpty()) 0 else values[0][0].size
	var nd = zeros(depth, height, width)
	
	for (z in 0 until depth) {
		for (y in 0 until height) {
			for (x in 0 until width) {
				nd[z, y, x] = values[z][y][x]
			}
		}
	}
	
	return nd
}
