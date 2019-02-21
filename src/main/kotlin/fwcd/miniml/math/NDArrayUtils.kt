package fwcd.miniml.math

fun toOffset(indices: IntArray, shape: IntArray): Int {
	if (indices.size != shape.size) {
		throw IllegalArgumentException("Index count of $indices does not match shape $shape")
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

/** Creates a new rearranged array. */
fun IntArray.rearranged(vararg permutedIndices: Int): IntArray {
	if (permutedIndices.size != size) {
		throw IllegalArgumentException("Number of permutation indices ($permutedIndices) does not match array length ($size)")
	}
	
	return IntArray(size) { this[permutedIndices[it]] }
}
