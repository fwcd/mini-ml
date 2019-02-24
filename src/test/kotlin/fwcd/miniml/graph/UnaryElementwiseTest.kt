package fwcd.miniml.graph

import org.junit.Test
import org.junit.Assert.assertThat
import fwcd.miniml.graph.variable
import fwcd.miniml.math.scalarOfInt
import fwcd.miniml.math.scalarOf
import fwcd.miniml.math.approxEquals

class UnaryElementwiseTest {
	@Test
	fun testNegative() {
		val x = variable(scalarOf(5.8), name = "x")
		val neg = -x
		assertThat(neg.forward(), approxEquals(scalarOf(-5.8)))
		neg.backward()
		assertThat(x.gradient!!, approxEquals(scalarOfInt(-1)))
	}
	
	@Test
	fun testReciprocal() {
		val x = variable(scalarOf(5.8), name = "x")
		val rec = x.reciprocal()
		assertThat(rec.forward(), approxEquals(scalarOf(0.1724137931034483)))
		rec.backward()
		assertThat(x.gradient!!, approxEquals(scalarOf(-0.0297)))
	}
	
	@Test
	fun testSigmoid() {
		val x = variable(scalarOf(4.5), name = "x")
		val sig = x.sigmoid()
		assertThat(sig.forward(), approxEquals(scalarOf(0.989)))
		sig.backward()
		assertThat(x.gradient!!, approxEquals(scalarOf(0.0109)))
	}
	
	@Test
	fun testTanh() {
		val x = variable(scalarOf(0.7), name = "x")
		val th = x.tanh()
		assertThat(th.forward(), approxEquals(scalarOf(0.6044)))
		th.backward()
		assertThat(x.gradient!!, approxEquals(scalarOf(0.6347)))
	}
	
	@Test
	fun testSin() {
		val x = variable(scalarOf(9.3), name = "x")
		val s = x.sin()
		assertThat(s.forward(), approxEquals(scalarOf(0.1245)))
		s.backward()
		assertThat(x.gradient!!, approxEquals(scalarOf(-0.9922)))
	}
	
	@Test
	fun testCos() {
		val x = variable(scalarOf(7.7), name = "x")
		val c = x.cos()
		assertThat(c.forward(), approxEquals(scalarOf(0.153373)))
		c.backward()
		assertThat(x.gradient!!, approxEquals(scalarOf(-0.9882)))
	}
	
	@Test
	fun testTan() {
		val x = variable(scalarOf(2.1), name = "x")
		val t = x.tan()
		assertThat(t.forward(), approxEquals(scalarOf(-1.709846)))
		t.backward()
		assertThat(x.gradient!!, approxEquals(scalarOf(3.9236)))
	}
	
	@Test
	fun testLog() {
		val x = variable(scalarOf(2.1), name = "x")
		val l = x.log()
		assertThat(l.forward(), approxEquals(scalarOf(0.7419373447293773)))
		l.backward()
		assertThat(x.gradient!!, approxEquals(scalarOf(0.4762)))
	}
	
	@Test
	fun testExp() {
		val x = variable(scalarOf(2.1), name = "x")
		val e = x.exp()
		assertThat(e.forward(), approxEquals(scalarOf(8.16616991256765)))
		e.backward()
		assertThat(x.gradient!!, approxEquals(scalarOf(8.16616991256765)))
	}
}
