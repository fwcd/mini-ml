package fwcd.miniml.math

import java.util.ArrayDeque

/** Represents a shape permutation that swaps rows/columns of a matrix (= rank 2 nd-array). */
private val MATRIX_TRANSPOSE: IntArray = intArrayOf(1, 0)

/**
 * A mutable n-dimensional array. However, all non-assign
 * operations will not mutate this array. For indexing
 * the row-major order is used.
 *
 * <p>In machine learning, these arrays are often referred
 * to as "tensors", even though this definition of "tensor"
 * is mathematically imprecise.</p>
 */
class NDArray(
	val values: DoubleArray,
	var shape: IntArray
) {
	var mutable = true
	val flatSize
		get() = values.size
	val rank
		get() = shape.size
	
	constructor(shape: IntArray) : this(DoubleArray(toFlattenedSize(shape)), shape) {}
	
	init {
		if (values.size != toFlattenedSize(shape)) {
			throw ShapeMismatchException("Value count of ${values.contentToString()} does not match shape ${shape.contentToString()}")
		}
	}
	
	/** Gets an element from the nd-array. */
	operator fun get(vararg indices: Int): Double = values[toOffset(indices, shape)]
	
	/** Sets an element in this nd-array. */
	operator fun set(vararg indices: Int, value: Double) = setAt(indices, value)
	
	private fun setAt(indices: IntArray, value: Double) {
		ensureMutable()
		values[toOffset(indices, shape)] = value
	}
	
	/** Computes the sum of this and another nd-array. */
	operator fun plus(rhs: NDArray): NDArray = zip(rhs) { a, b -> a + b }
	
	/** Computes the difference between this and another nd-array. */
	operator fun minus(rhs: NDArray): NDArray = zip(rhs) { a, b -> a - b }
	
	/** Scales this nd-array. */
	operator fun times(scalar: Double): NDArray = map { it * scalar }
	
	/** Performs elementwise multiplication. */
	operator fun times(rhs: NDArray): NDArray = zip(rhs) { a, b -> a * b }
	
	/** Divides this nd-array (assuming both nd-arrays are of rank zero). */
	operator fun div(rhs: NDArray): NDArray = zip(rhs) { a, b -> a / b }
	
	/** Scales this nd-array (assuming both nd-arrays are of rank zero). */
	operator fun rem(rhs: NDArray): NDArray = zip(rhs) { a, b -> a % b }
	
	/** Computes the multiplicative inverse of this nd-array. */
	fun reciprocal() = map { 1.0 / it }
	
	/** Computes the additive inverse of this nd-array. */
	operator fun unaryMinus() = map { -it }
	
	/** Returns itself. */
	operator fun unaryPlus() = this
	
	/** Computes the multiplicative inverse of this nd-array in-place. */
	fun reciprocalAssign() = mapAssign { 1.0 / it }
	
	/** Computes the additive inverse of this nd-array in-place. */
	fun unaryMinusAssign() = mapAssign { -it }
	
	/** Computes the elementwise square of this */
	fun square() = this * this
	
	/** Adds an nd-array to this one in-place. */
	operator fun plusAssign(rhs: NDArray) = zipAssign(rhs) { a, b -> a + b }
	
	/** Subtracts an nd-array from this one in-place. */
	operator fun minusAssign(rhs: NDArray) = zipAssign(rhs) { a, b -> a - b }
	
	/** Scales this nd-array in-place. */
	operator fun timesAssign(scalar: Double) {
		ensureMutable()
		for (i in 0 until flatSize) {
			values[i] *= scalar
		}
	}
	
	/** Multiplies this nd-array with another elementwise in-place. */
	operator fun timesAssign(rhs: NDArray) = zipAssign(rhs) { a, b -> a * b }
	
	/** Divides this nd-array by another elementwise in-place. */
	operator fun divAssign(rhs: NDArray) = zipAssign(rhs) { a, b -> a / b }
	
	/** Computes the remainder of nd-array with another elementwise in-place. */
	operator fun remAssign(rhs: NDArray) = zipAssign(rhs) { a, b -> a % b }
	
	/** Fetches the 1st inner array (to support destructurings) */
	operator fun component1(): NDArray = innerNDArray(0)
	
	/** Fetches the 2nd inner array (to support destructurings) */
	operator fun component2(): NDArray = innerNDArray(1)
	
	/** Fetches the 3rd inner array (to support destructurings) */
	operator fun component3(): NDArray = innerNDArray(2)
	
	/** Fetches the 4th inner array (to support destructurings) */
	operator fun component4(): NDArray = innerNDArray(3)
	
	/** Fetches the 5th inner array (to support destructurings) */
	operator fun component5(): NDArray = innerNDArray(4)
	
	/**
	 * Fetches the ..th inner nd-array. The resulting
	 * nd-array will have a rank of this.rank - 1. In
	 * case of a matrix, this function will return the
	 * row at the specified index.
	 */
	fun innerNDArray(index: Int): NDArray {
		if (rank == 0) {
			throw IllegalStateException("Cannot access inner nd-array of a scalar (rank has to be >0)")
		}
		val outerStride = stride(shape, 0)
		val start = outerStride * index
		val end = start + outerStride
		val innerShape = shape.copyOfRange(1, shape.size)
		val innerValues = values.copyOfRange(start, end)
		return NDArray(innerValues, innerShape)
	}
	
	/** Combines this nd-array elementwise with another using an operation function. */
	inline fun zip(rhs: NDArray, operation: (Double, Double) -> Double): NDArray = copy().also { it.zipAssign(rhs, operation) }
	
	/** Maps this nd-array elementwise using given mapper function. */
	inline fun map(mapper: (Double) -> Double): NDArray = copy().also { it.mapAssign(mapper) }
	
	/** Maps this nd-array elementwise in-place using given mapper function. */
	inline fun mapAssign(mapper: (Double) -> Double) {
		ensureMutable()
		for (i in 0 until flatSize) {
			values[i] = mapper(values[i])
		}
	}
	
	/** Combines this nd-array elementwise in-place with another using an operation function. */
	inline fun zipAssign(rhs: NDArray, operation: (Double, Double) -> Double) {
		ensureMutable()
		if (flatSize != rhs.flatSize) {
			throw ShapeMismatchException("Can not perform elementwise operation on NDArrays with different flat sizes: $flatSize and ${rhs.flatSize}")
		}
		for (i in 0 until flatSize) {
			values[i] = operation(values[i], rhs.values[i])
		}
	}
	
	/** Reduces this nd-array elementwise. */
	inline fun reduce(operation: (Double, Double) -> Double): Double = values.reduce(operation)
	
	/** Computes the elementwise sum. */
	fun reduceSum(): Double = reduce { a, b -> a + b }
	
	/** Computes the vector dot product of this nd-another and another, assuming both nd-arrays have rank 1. */
	infix fun dot(rhs: NDArray): Double {
		if (rank != 1 || rhs.rank != 1) {
			throw ShapeMismatchException("The dot product is only defined for vectors, not arrays of ranks $rank/${rhs.rank}")
		} else if (shape[0] != rhs.shape[0]) {
			throw ShapeMismatchException("The dot product requires two vectors of equal size, not ${shape[0]} and ${rhs.shape[0]}")
		}
		
		var result: Double = 0.0
		
		for (i in 0 until shape[0]) {
			result += values[i] * rhs.values[i]
		}
		
		return result
	}
	
	/** Permutes the dimensions of this array (swaps rows and columns by default). */
	fun transpose(permutation: IntArray = MATRIX_TRANSPOSE): NDArray {
		if (rank != permutation.size) {
			throw ShapeMismatchException("Number of permutation indices ${permutation.contentToString()} does not match shape ${shape.contentToString()}")
		}
		
		val transposed = NDArray(shape.rearranged(*permutation))
		
		traverse {
			transposed.setAt(it.rearranged(*permutation), get(*it))
		}
		
		return transposed
	}
	
	/** Reshapes this nd-array in-place. */
	fun reshapeAssign(vararg newShape: Int) {
		ensureMutable()
		
		if (toFlattenedSize(newShape) != flatSize) {
			throw ShapeMismatchException("Flattened size ${toFlattenedSize(newShape)} (of ${newShape.contentToString()}) does not match flatSize $flatSize")
		}
		
		shape = newShape
	}
	
	/** Creates a new reshaped nd-array. */
	fun reshape(vararg newShape: Int) = NDArray(values.copyOf(), newShape)
	
	/**
	 * Traverses all possible coordinates in this array.
	 * NOTE that the array passed to the consumer will
	 * be mutated, thus if the caller needs to store it,
	 * a copy should be created.
	 */
	internal inline fun traverse(consumer: (IntArray) -> Unit) {
		val coords = IntArray(rank)
		val lastIndex = rank - 1
		var finished = false
		
		while (!finished) {
			consumer(coords)
			coords[lastIndex]++
			var j = lastIndex
			while (j >= 0 && coords[j] >= shape[j]) {
				if (j == 0) {
					finished = true
				} else {
					coords[j] = 0
					coords[j - 1]++
				}
				j--
			}
		}
	}
	
	/** Matrix-multiplies this nd-array with another, assuming both nd-arrays have rank 2. */
	infix fun matmul(rhs: NDArray): NDArray {
		if (shape[1] != rhs.shape[0]) {
			throw ShapeMismatchException("The width of the left matrix (${shape[0]}) has to match the height of the right matrix (${shape[1]})")
		} else if (rank == 2 && rhs.rank == 1) {
			// Multiply matrix by vector (assuming the vector is a column)
			val dotCount = shape[1]
			val productHeight = shape[0]
			val product = NDArray(intArrayOf(productHeight))
			
			for (y in 0 until productHeight) {
				var dot: Double = 0.0
				for (i in 0 until dotCount) {
					dot += this[y, i] * rhs[i]
				}
				product[y] = dot
			}
			
			return product
		} else if (rank != 2 || rhs.rank != 2) {
			throw ShapeMismatchException("Matrix multiplication is only defined for two-dimensional matrices, not arrays of ranks $rank/${rhs.rank}")
		} else {
			// Multiply matrix by matrix
			val dotCount = shape[1]
			val productHeight = shape[0]
			val productWidth = rhs.shape[1]
			val product = NDArray(intArrayOf(productHeight, productWidth))
			
			for (y in 0 until productHeight) {
				for (x in 0 until productWidth) {
					var dot: Double = 0.0
					for (i in 0 until dotCount) {
						dot += this[y, i] * rhs[i, x]
					}
					product[y, x] = dot
				}
			}
			
			return product
		}
	}
	
	/** Returns the value if this nd-array is a scalar (or 1-element vector/matrix/...). */
	fun toScalar(): Double? = if (flatSize == 1) values[0] else null
	
	/** Unwraps and returns the value if this nd-array is a scalar (or 1-element vector/matrix/...), otherwise throws an exception. */
	fun expectScalar(): Double = toScalar() ?: throw IllegalStateException("${toString()} is not a scalar")
	
	/** Copies this nd-array. */
	fun copy() = NDArray(values.copyOf(), shape.copyOf())
	
	/** Checks whether this nd-array equals another one within a given tolerance. */
	fun equals(rhs: NDArray, tolerance: Double): Boolean {
		if (!rhs.shape.contentEquals(shape)) {
			return false
		}
		for (i in 0 until flatSize) {
			if (Math.abs(values[i] - rhs.values[i]) > tolerance) {
				return false
			}
		}
		return true
	}
	
	/** Reshapes this nd-array to a column matrix, assuming it has rank 1 (i.e. is a vector) */
	fun asColumnMatrix(): NDArray {
		if (rank != 1) {
			throw ShapeMismatchException("Can only convert nd-arrays of rank 1 (vectors) to column matrices")
		}
		return reshape(flatSize, 1)
	}
	
	/** Reshapes this nd-array to a row matrix, assuming it has rank 1 (i.e. is a vector) */
	fun asRowMatrix(): NDArray {
		if (rank != 1) {
			throw ShapeMismatchException("Can only convert nd-arrays of rank 1 (vectors) to row matrices")
		}
		return reshape(1, flatSize)
	}
	
	private inner class Stringifier(private var offset: Int = 0, private val builder: StringBuilder = StringBuilder()) {
		fun stringify(dimIndex: Int) {
			if (dimIndex == (rank - 1)) {
				builder.append(values[offset])
				offset++
			} else {
				builder.append('[')
				val last = shape[dimIndex + 1]
				for (i in 0 until last) {
					stringify(dimIndex + 1)
					if ((i + 1) < last) {
						builder.append(", ")
					}
				}
				builder.append(']')
			}
		}
		
		override fun toString(): String {
			stringify(-1)
			return builder.toString()
		}
	}
	
	fun ensureMutable() {
		if (!mutable) {
			throw IllegalStateException("Mutating an immutable NDArray is not supported!")
		}
	}
	
	/** Converts this nd-array to a string. */
	override fun toString(): String = Stringifier().toString()
}
