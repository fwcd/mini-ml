/**
 * A mutable n-dimensional array.
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
	
	/** Adds an nd-array to this one in-place. */
	operator fun plusAssign(rhs: NDArray) {
		if (flatSize != rhs.flatSize) {
			throw IllegalArgumentException("Can not perform addition on NDArrays with different flat sizes: $flatSize and ${rhs.flatSize}")
		}
		
		for (i in 0 until flatSize) {
			values[i] += rhs.values[i]
		}
	}
	
	/** Subtracts an nd-array from this one in-place. */
	operator fun minusAssign(rhs: NDArray) {
		if (flatSize != rhs.flatSize) {
			throw IllegalArgumentException("Can not perform subtraction on NDArrays with different flat sizes: $flatSize and ${rhs.flatSize}")
		}
		
		for (i in 0 until flatSize) {
			values[i] -= rhs.values[i]
		}
	}
	
	/** Scales this nd-array in-place. */
	operator fun timesAssign(scalar: Double) {
		for (i in 0 until flatSize) {
			values[i] *= scalar
		}
	}
	
	/** Matrix-multiplies this nd-array with another, assuming both matrices have rank 2. */
	fun matmul(rhs: NDArray): NDArray {
		if (rank != 2 || rhs.rank != 2) {
			throw IllegalArgumentException("Matrix multiplication is only defined for two-dimensional matrices, not $rank/${rhs.rank} dimensions")
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
