package fwcd.miniml.graph

import org.junit.Test
import org.junit.Assert.assertThat
import fwcd.miniml.math.scalarOf
import fwcd.miniml.math.approxEquals
import org.hamcrest.Matchers.*

class GraphTest {
	@Test
	fun testGraph() {
		val a = placeholder(scalarOf(20.5))
		val b = constant(scalarOf(10.0))
		val c = variable(scalarOf(98.1))
		val d = variable(scalarOf(-20.2))
		val out = ((a * c.square()) - (a * b)) + d
		
		assertThat(out.forward(), approxEquals(scalarOf(197058.805)))
	}
}
