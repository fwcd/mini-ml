/**
 * A mutable n-dimensional array. However, all non-assign
 * operations will not mutate this array.
 */
class NDArray(
	val values: DoubleArray,
	val shape: IntArray
) {
	val flatSize
		get() = values.size
	val rank
		get() = shape.size
	
	constructor(shape: IntArray) : this(DoubleArray(toFlattenedSize(shape)), shape) {}
	
	init {
		if (values.size != toFlattenedSize(shape)) {
			throw IllegalArgumentException("Value count of $values does not match shape $shape")
		}
	}
	
	/** Gets an element from the nd-array. */
	operator fun get(vararg indices: Int): Double = values[toOffset(indices, shape)]
	
	/** Sets an element in this nd-array. */
	operator fun set(vararg indices: Int, value: Double) {
		values[toOffset(indices, shape)] = value
	}
	
	/** Computes the sum of this and another nd-array. */
	operator fun plus(rhs: NDArray): NDArray = copy().also { it += rhs }
	
	/** Computes the difference between this and another nd-array. */
	operator fun minus(rhs: NDArray): NDArray = copy().also { it -= rhs }
	
	/** Scales this nd-array. */
	operator fun times(scalar: Double): NDArray = copy().also { it *= scalar }
	
	/** Performs elementwise multiplication. */
	operator fun times(rhs: NDArray): NDArray = copy().also { it *= rhs }
	
	/** Divides this nd-array (assuming both nd-arrays are of rank zero). */
	operator fun div(scalar: NDArray): NDArray = copy().also { it /= scalar }
	
	/** Scales this nd-array (assuming both nd-arrays are of rank zero). */
	operator fun rem(scalar: NDArray): NDArray = copy().also { it %= scalar }
	
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
	
	/** Adds an nd-array to this one in-place. */
	operator fun plusAssign(rhs: NDArray) = zipAssign(rhs) { a, b -> a + b }
	
	/** Subtracts an nd-array from this one in-place. */
	operator fun minusAssign(rhs: NDArray) = zipAssign(rhs) { a, b -> a - b }
	
	/** Scales this nd-array in-place. */
	operator fun timesAssign(scalar: Double) {
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
	
	/** Combines this nd-array elementwise with another using an operation function. */
	inline fun zip(rhs: NDArray, operation: (Double, Double) -> Double): NDArray = copy().also { it.zipAssign(rhs, operation) }
	
	/** Maps this nd-array elementwise using given mapper function. */
	inline fun map(mapper: (Double) -> Double): NDArray = copy().also { it.mapAssign(mapper) }
	
	/** Maps this nd-array elementwise in-place using given mapper function. */
	inline fun mapAssign(mapper: (Double) -> Double) {
		for (i in 0 until flatSize) {
			values[i] = mapper(values[i])
		}
	}
	
	/** Combines this nd-array elementwise in-place with another using an operation function. */
	inline fun zipAssign(rhs: NDArray, operation: (Double, Double) -> Double) {
		if (flatSize != rhs.flatSize) {
			throw IllegalArgumentException("Can not perform elementwise operation on NDArrays with different flat sizes: $flatSize and ${rhs.flatSize}")
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
	fun dot(rhs: NDArray): Double {
		if (rank != 1 || rhs.rank != 1) {
			throw IllegalArgumentException("The dot product is only defined for vectors, not arrays of ranks $rank/${rhs.rank}")
		} else if (shape[0] != rhs.shape[0]) {
			throw IllegalArgumentException("The dot product requires two vectors of equal size, not ${shape[0]} and ${rhs.shape[0]}")
		}
		
		var result: Double = 0.0
		
		for (i in 0 until shape[0]) {
			result += values[i] * rhs.values[i]
		}
		
		return result
	}
	
	/** Matrix-multiplies this nd-array with another, assuming both nd-arrays have rank 2. */
	fun matmul(rhs: NDArray): NDArray {
		if (rank != 2 || rhs.rank != 2) {
			throw IllegalArgumentException("Matrix multiplication is only defined for two-dimensional matrices, not arrays of ranks $rank/${rhs.rank}")
		} else if (shape[1] != rhs.shape[0]) {
			throw IllegalArgumentException("The width of the left matrix (${shape[0]}) has to match the height of the right matrix (${shape[1]})")
		}
		
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
	
	/** Converts this nd-array to a string. */
	override fun toString(): String = Stringifier().toString()
}
