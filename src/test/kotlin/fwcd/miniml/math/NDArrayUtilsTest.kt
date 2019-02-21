package fwcd.miniml.math

import org.junit.Test
import org.junit.Assert.assertArrayEquals

class NDArrayUtilsTest {
	@Test
	fun testRearrange() {
		val a = intArrayOf(9, 1, 32)
		a.rearrange(2, 0, 1)
		assertArrayEquals(intArrayOf(32, 9, 1), a)
		a.rearrange(0, 1, 2)
		assertArrayEquals(intArrayOf(32, 9, 1), a)
		
		val b = intArrayOf(0, 1, 9, 4, 7)
		assertArrayEquals(intArrayOf(9, 4, 1, 7, 0), b.rearranged(2, 3, 1, 4, 0))
		assertArrayEquals(intArrayOf(0, 1, 9, 4, 7), b)
	}
}
