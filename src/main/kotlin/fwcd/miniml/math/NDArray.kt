/**
 * A mutable n-dimensional array.
 */
class NDArray(
	val values: DoubleArray,
	val shape: IntArray
) {
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
	
	fun copy() = NDArray(values.copyOf(), shape.copyOf())
}
