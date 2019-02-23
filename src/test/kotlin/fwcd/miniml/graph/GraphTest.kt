package fwcd.miniml.graph

import org.junit.Test
import org.junit.Assert.assertThat
import fwcd.miniml.math.scalarOf
import fwcd.miniml.math.scalarOfInt
import fwcd.miniml.math.zeros
import fwcd.miniml.math.randoms
import fwcd.miniml.math.approxEquals
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
	fun testScalarGraphOptimization() {
		val x = placeholder(zeros())
		val w = variable(randoms(-10.0, 10.0))
		val b = variable(randoms(-10.0, 10.0))
		val expected = x.square()
		val output = (w * x) + b
		val cost = (expected - output).square()
		
		for (i in 0 until 1000) {
			x.value = randoms(-10.0, 10.0)
			val currentCost = cost.forward()
			cost.backward()
			w.apply(-w.gradient!! * 0.1)
			b.apply(-b.gradient!! * 0.1)
		}
		
		// TODO: Use fixed values and add assertions
	}
	
	@Test
	fun testMatMulGraph() {
		val x = placeholder(zeros(3))
		val w = variable(randoms(-10.0, 10.0, 3, 3))
		val b = variable(randoms(-10.0, 10.0, 3))
		val expected = x.square()
		val output = (w matmul x) + b
		val cost = (expected - output).square().reduceSum()
		val dataVec = mutableListOf<Double>() // DEBUG
		
		for (i in 0 until 1000) {
			x.value = randoms(0.0, 3.0, 3)
			val currentCost = cost.forward()
			cost.backward()
			w.apply(-w.gradient!! * 0.01)
			b.apply(-b.gradient!! * 0.01)
			dataVec.add(currentCost.expectScalar()) // DEBUG
		}
		
		// TODO: Use fixed values and add assertions
		println(dataVec) // DEBUG
	}
}
