package fwcd.miniml.graph

import org.junit.Test
import org.junit.Assert.assertThat
import fwcd.miniml.math.scalarOf
import fwcd.miniml.math.scalarOfInt
import fwcd.miniml.math.zeros
import fwcd.miniml.math.randoms
import fwcd.miniml.math.approxEquals
import fwcd.miniml.math.ONE
import org.hamcrest.Matchers.*

class GraphTest {
	@Test
	fun testSimpleScalarGraph() {
		val a = placeholder(scalarOf(20.5))
		val b = variable(scalarOf(10.0))
		val c = variable(scalarOf(98.1))
		val d = variable(scalarOf(-20.2))
		val output = ((a * c.square()) - (a * b)) + d
		
		assertThat(output.forward(), approxEquals(scalarOf(197058.805)))
		
		// Test whether gradients are computed correctly
		output.backward()
		assertThat(c.gradient!!, approxEquals(scalarOfInt(41) * c.cachedForward()!!)) // doutput / dc = 41 * c
		assertThat(d.gradient!!, approxEquals(ONE)) // doutput / dd = 1
	}
	
	@Test
	fun testMatMulGraph() {
		// val x = placeholder(zeros(3))
		// val w = variable(randoms(3, 3))
		// val b = variable(randoms())
		// TODO
	}
}
