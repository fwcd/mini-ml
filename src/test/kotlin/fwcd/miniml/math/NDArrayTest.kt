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
	}
}
