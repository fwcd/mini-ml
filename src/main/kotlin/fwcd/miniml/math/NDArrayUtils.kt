fun toOffset(indices: IntArray, shape: IntArray): Int {
	if (indices.size != shape.size) {
		throw IllegalArgumentException("Index count of $indices does not match shape $shape")
	}
	
	var offset: Int = 0
	var factor: Int = 0
	
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
