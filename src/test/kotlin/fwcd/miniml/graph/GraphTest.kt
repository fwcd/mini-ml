package fwcd.miniml.graph

import org.junit.Test
import org.junit.Ignore
import org.junit.Assert.assertThat
import fwcd.miniml.math.scalarOf
import fwcd.miniml.math.scalarOfInt
import fwcd.miniml.math.zeros
import fwcd.miniml.math.randoms
import fwcd.miniml.math.approxEquals
import fwcd.miniml.math.matrixOf
import fwcd.miniml.math.vectorOf
import fwcd.miniml.math.rowOf
import fwcd.miniml.math.ONE
import java.io.File
import org.hamcrest.Matchers.*

class GraphTest {
	@Test
	fun testScalarGraphGradients1() {
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
	fun testScalarGraphGradients2() {
		val x = placeholder(scalarOf(3.5))
		val w = variable(scalarOf(-18.2))
		val b = variable(scalarOf(23.4))
		val c = variable(scalarOfInt(-4))
		val y = -(((w * x) + b) * c)
		
		assertThat(y.forward(), approxEquals(scalarOf(-161.2)))
		
		y.backward()
		assertThat(w.gradient!!, approxEquals(scalarOfInt(14)))
		assertThat(b.gradient!!, approxEquals(scalarOfInt(4)))
		assertThat(c.gradient!!, approxEquals(scalarOf(40.3)))
	}
	
	@Test
	fun testScalarGraphGradients3() {
		val x = placeholder(scalarOf(3.5))
		val w = variable(scalarOf(-18.2))
		val b = variable(scalarOf(23.4))
		val y = (w * x) + b
		
		assertThat(y.forward(), approxEquals(scalarOf(-40.3)))
		
		y.backward()
		assertThat(w.gradient!!, approxEquals(scalarOf(3.5)))
		assertThat(b.gradient!!, approxEquals(ONE))
	}
	
	@Test
	fun testMatMulGraphGradients1() {
		val x = placeholder(vectorOf(2.0, 1.5), name = "x")
		val w = variable(matrixOf(
			rowOf(23.4, 25.0),
			rowOf(-10.8, 0.8)
		), name = "w")
		val b = variable(vectorOf(23.3, 32.1), name = "b")
		val expected = x.square()
		val output = (w matmul x) + b
		val diffs = (expected - output).square()
		val cost = diffs.reduceSum()
		
		assertThat(diffs.forward(), approxEquals(vectorOf(10732.9609, 89.3025)))
		assertThat(output.forward(), approxEquals(vectorOf(107.6, 11.7)))
		assertThat(expected.forward(), approxEquals(vectorOf(4.0, 2.25)))
		assertThat(cost.forward(), approxEquals(scalarOf(10822.2637)))
		
		cost.backward()
		assertThat(cost.gradient!!, approxEquals(ONE))
		assertThat(diffs.gradient!!, approxEquals(vectorOf(1.0, 1.0)))
		// assertThat(output.gradient!!, approxEquals(vectorOf(207.2, 18.9)))
		assertThat(w.gradient!!, approxEquals(matrixOf(
			rowOf(414.4, 310.8),
			rowOf(37.8, 28.35)
		)))
		assertThat(b.gradient!!, approxEquals(vectorOf(207.2, 18.9)))
	}
	
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
	@Ignore
	fun testScalarGraphOptimization() {
		val x = placeholder(zeros())
		val w = variable(randoms(-10.0, 10.0))
		val b = variable(randoms(-10.0, 10.0))
		val expected = x * 3.0
		val output = (w * x) + b
		val cost = (expected - output).square()
		val learningRate = 0.0001

		for (i in 0 until 2000) {
			x.value = randoms(-10.0, 10.0)
			cost.forward()
			cost.clearGradients()
			cost.backward()
			w.apply(-w.gradient!! * learningRate)
			b.apply(-b.gradient!! * learningRate)
		}

		// TODO: Use fixed values and add assertions
	}
	
	@Test
	@Ignore
	fun testMatMulGraphOptimization() {
		val x = placeholder(zeros(3))
		val w = variable(randoms(-10.0, 10.0, 3, 3))
		val b = variable(randoms(-10.0, 10.0, 3))
		val expected = x.square()
		val output = (w matmul x) + b
		val cost = (expected - output).square().reduceSum()
		
		for (i in 0 until 1000) {
			x.value = randoms(0.0, 3.0, 3)
			cost.forward()
			cost.backward()
			w.apply(-w.gradient!! * 0.01)
			b.apply(-b.gradient!! * 0.01)
		}
		
		// TODO: Use fixed values and add assertions
	}
}
