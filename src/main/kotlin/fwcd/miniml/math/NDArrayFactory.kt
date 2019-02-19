fun zeros(vararg shape: Int) = NDArray(DoubleArray(toFlattenedSize(shape)), shape)

fun ones(vararg shape: Int) = fill(1.0, *shape)

fun fill(value: Double, vararg shape: Int) = NDArray(DoubleArray(toFlattenedSize(shape)) { value }, shape)

fun vectorOf(vararg values: Double) = NDArray(values, intArrayOf(values.size))

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
