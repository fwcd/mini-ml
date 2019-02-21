package fwcd.miniml.math

import org.junit.Test
import org.junit.Assert.assertArrayEquals

class NDArrayUtilsTest {
	@Test
	fun testRearrange() {
		val b = intArrayOf(0, 1, 9, 4, 7)
		assertArrayEquals(intArrayOf(9, 4, 1, 7, 0), b.rearranged(2, 3, 1, 4, 0))
		assertArrayEquals(intArrayOf(0, 1, 9, 4, 7), b)
	}
}
