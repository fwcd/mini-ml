package fwcd.miniml.math

fun toOffset(indices: IntArray, shape: IntArray): Int {
	if (indices.size == 1 && shape.size == 0) {
		// Return scalar value
		return indices[0]
	} else if (indices.size != shape.size) {
		throw IllegalArgumentException("Index count of ${indices.contentToString()} does not match shape ${shape.contentToString()}")
	}
	
	var offset: Int = 0
	var factor: Int = 1
	
	for (i in (indices.size - 1) downTo 0) {
		offset += indices[i] * factor
		factor *= shape[i]
	}
	
	return offset
}

fun toFlattenedSize(shape: IntArray): Int {
	var size = 1
	for (dim in shape) {
		size *= dim
	}
	return size
}

fun stride(shape: IntArray, dimIndex: Int): Int {
	if (dimIndex < 0) {
		throw IllegalArgumentException("Dimension index has to be larger or equal to zero")
	} else if (dimIndex >= shape.size) {
		throw IllegalArgumentException("Dimension index cannot exceed rank (or shape length)")
	}
	var result = 1
	for (i in (dimIndex + 1) until shape.size) {
		result *= shape[i]
	}
	return result
}

/** Creates a new rearranged array. */
fun IntArray.rearranged(vararg permutedIndices: Int): IntArray {
	if (permutedIndices.size != size) {
		throw IllegalArgumentException("Number of permutation indices (${permutedIndices.contentToString()}) does not match array length (size)")
	}
	
	return IntArray(size) { this[permutedIndices[it]] }
}
