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

fun IntArray.rearrange(vararg permutedIndices: Int) {
	if (permutedIndices.size != size) {
		throw IllegalArgumentException("Number of permutation indices ($permutedIndices) does not match array length ($size)")
	}
	
	// Source: https://www.geeksforgeeks.org/reorder-a-array-according-to-given-indexes/
	
	for (i in 0 until size) {
		// While permutedIndices[i] and this[i] not fixed
		while (permutedIndices[i] != i) {
			// Store values of target position
			val oldTargetIndex = permutedIndices[permutedIndices[i]]
			val oldTargetEntry = this[permutedIndices[i]]
			
			// Place this[i] at its target position and copy corrected index
			this[permutedIndices[i]] = this[i]
			permutedIndices[permutedIndices[i]] = permutedIndices[i]
			
			// Copy old target value to this[i] and permutedIndices[i]
			permutedIndices[i] = oldTargetIndex
			this[i] = oldTargetEntry
		}
	}
}

fun IntArray.rearranged(vararg permutedIndices: Int): IntArray {
	if (permutedIndices.size != size) {
		throw IllegalArgumentException("Number of permutation indices ($permutedIndices) does not match array length ($size)")
	}
	
	return IntArray(size) { this[permutedIndices[it]] }
}
