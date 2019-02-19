import org.junit.Test
import org.junit.Assert.assertThat
import org.junit.Assert.assertArrayEquals
import org.hamcrest.Matchers.*

private const val EPS: Double = 0.01

class NDArrayTest {
	@Test
	fun testNDArray() {
		val emptyVec = vectorOf()
		assertThat(emptyVec.flatSize, equalTo(0))
		assertArrayEquals(intArrayOf(0), emptyVec.shape)
		
		val vec = vectorOf(23.0, -1.3, 921.4)
		assertThat(vec.flatSize, equalTo(3))
		assertArrayEquals(intArrayOf(3), vec.shape)
		assertThat(toOffset(intArrayOf(0), vec.shape), equalTo(0))
		assertThat(toOffset(intArrayOf(1), vec.shape), equalTo(1))
		assertThat(toOffset(intArrayOf(2), vec.shape), equalTo(2))
		assertThat(vec[0], closeTo(23.0, EPS))
		assertThat(vec[1], closeTo(-1.3, EPS))
		assertThat(vec[2], closeTo(921.4, EPS))
		
		val mat = matrixOf(
			rowOf(2.0, 3.0),
			rowOf(5.0, 4.0),
			rowOf(0.0, -10.2)
		)
		assertThat(mat.flatSize, equalTo(6))
		assertArrayEquals(doubleArrayOf(2.0, 3.0, 5.0, 4.0, 0.0, -10.2), mat.values, EPS)
		assertArrayEquals(intArrayOf(3, 2), mat.shape)
		assertThat(mat[0, 0], closeTo(2.0, EPS))
		assertThat(mat[0, 1], closeTo(3.0, EPS))
		assertThat(mat[1, 0], closeTo(5.0, EPS))
		assertThat(mat[1, 1], closeTo(4.0, EPS))
		assertThat(mat[2, 0], closeTo(0.0, EPS))
		assertThat(mat[2, 1], closeTo(-10.2, EPS))
		
		val mat3D = matrix3DOf(
			arrayOf(
				rowOf(2.0, -2.0),
				rowOf(1.0, -1.0)
			),
			arrayOf(
				rowOf(-32.4, 232.1),
				rowOf(-0.2, 1.1)
			)
		)
		assertThat(mat3D.flatSize, equalTo(8))
		assertArrayEquals(doubleArrayOf(2.0, -2.0, 1.0, -1.0, -32.4, 232.1, -0.2, 1.1), mat3D.values, EPS)
		assertArrayEquals(intArrayOf(2, 2, 2), mat3D.shape)
		assertThat(mat3D[0, 0, 0], closeTo(2.0, EPS))
		assertThat(mat3D[0, 0, 1], closeTo(-2.0, EPS))
		assertThat(mat3D[0, 1, 0], closeTo(1.0, EPS))
		assertThat(mat3D[0, 1, 1], closeTo(-1.0, EPS))
		assertThat(mat3D[1, 0, 0], closeTo(-32.4, EPS))
		assertThat(mat3D[1, 0, 1], closeTo(232.1, EPS))
		assertThat(mat3D[1, 1, 0], closeTo(-0.2, EPS))
		assertThat(mat3D[1, 1, 1], closeTo(1.1, EPS))
	}
}