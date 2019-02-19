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
	
	init {
		if (values.size != toFlattenedSize(shape)) {
			throw IllegalArgumentException("Value count of $values does not match shape $shape")
		}
	}
	
	/** Gets an element from the nd-array. */
	operator fun get(vararg indices: Int): Double = values[toOffset(indices, shape)]
	
	operator fun set(vararg indices: Int, value: Double) {
		values[toOffset(indices, shape)] = value
	}
	
	operator fun plus(rhs: NDArray): NDArray = copy().also { it += rhs }
	
	operator fun minus(rhs: NDArray): NDArray = copy().also { it -= rhs }
	
	operator fun times(scalar: Double): NDArray = copy().also { it *= scalar }
	
	operator fun plusAssign(rhs: NDArray) {
		if (flatSize != rhs.flatSize) {
			throw IllegalArgumentException("Can not perform addition on NDArrays with different flat sizes: $flatSize and ${rhs.flatSize}")
		}
		
		for (i in 0 until flatSize) {
			values[i] += rhs.values[i]
		}
	}
	
	operator fun minusAssign(rhs: NDArray) {
		if (flatSize != rhs.flatSize) {
			throw IllegalArgumentException("Can not perform subtraction on NDArrays with different flat sizes: $flatSize and ${rhs.flatSize}")
		}
		
		for (i in 0 until flatSize) {
			values[i] -= rhs.values[i]
		}
	}
	
	operator fun timesAssign(scalar: Double) {
		for (i in 0 until flatSize) {
			values[i] *= scalar
		}
	}
	
	fun copy() = NDArray(values.copyOf(), shape.copyOf())
	
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
	
	override fun toString(): String = Stringifier().toString()
}
