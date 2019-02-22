package fwcd.miniml.math

import org.junit.Test
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Assert.assertArrayEquals
import org.hamcrest.Matchers.*

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
	
	@Test
	fun testArithmetic() {
		val a = vectorOf(2.0, 4.0, 1.0)
		val b = vectorOf(-3.0, 2.3, 1.1)
		assertThat(a + b, approxEquals(vectorOf(-1.0, 6.3, 2.1)))
		assertThat(a - b, approxEquals(vectorOf(5.0, 1.7, -0.1)))
		assertThat(a * b, approxEquals(vectorOf(-6.0, 9.2, 1.1)))
		assertThat(a / b, approxEquals(vectorOf(-0.6666666, 1.73913043478, 0.90909090909)))
		assertThat(a % b, approxEquals(vectorOf(2.0, 1.7, 1.0)))
		assertThat(a.reciprocal(), approxEquals(vectorOfInts(1, 1, 1) / a))
		assertThat(a * -1.0, approxEquals(vectorOf(-2.0, -4.0, -1.0)))
		assertThat(a.reduceSum(), closeTo(7.0, EPS))
	}
	
	@Test
	fun testMatrixMultiplication() {
		val matA = matrixOf(
			rowOfInts(3, 2, 1),
			rowOfInts(1, 0, 2)
		)
		val matB = matrixOf(
			rowOfInts(1, 2),
			rowOfInts(0, 1),
			rowOfInts(4, 0)
		)
		assertThat(matA.matmul(matB), approxEquals(matrixOf(
			rowOfInts(7, 8),
			rowOfInts(9, 2)
		)))
		
		val vec = vectorOfInts(2, 5, 6)
		assertThat(matA.matmul(vec), approxEquals(vectorOfInts(22, 14)))
		assertThat(matA.matmul(vec.asColumnMatrix()), approxEquals(matA.matmul(vec).asColumnMatrix()))
	}
	
	@Test
	fun testVectorToMatrix() {
		val vec = vectorOfInts(5, 8, 3)
		assertThat(vec.asRowMatrix(), approxEquals(matrixOf(
			rowOfInts(5, 8, 3)
		)))
		assertThat(vec.asColumnMatrix(), approxEquals(matrixOf(
			rowOfInts(5),
			rowOfInts(8),
			rowOfInts(3)
		)))
	}
	
	@Test
	fun testDotProduct() {
		val vecA = vectorOfInts(4, 3, 2)
		val vecB = vectorOfInts(-1, 0, 1)
		assertThat(scalarOf(vecA.dot(vecB)), approxEquals(scalarOfInt(-2)))
	}
	
	@Test
	fun testTraverse() {
		val arr = matrix3DOf(
			arrayOf(
				rowOfInts(2, 5, 3),
				rowOfInts(0, 0, 1)
			),
			arrayOf(
				rowOfInts(-1, -13, 79),
				rowOfInts(1, 10, 43)
			),
			arrayOf(
				rowOfInts(1, 1, 2),
				rowOfInts(2, 9, 3)
			)
		)
		val traversed = mutableListOf<List<Int>>()
		arr.traverse {
			traversed.add(it.toList())
		}
		assertThat(traversed, contains(
			listOf(0, 0, 0),
			listOf(0, 0, 1),
			listOf(0, 0, 2),
			listOf(0, 1, 0),
			listOf(0, 1, 1),
			listOf(0, 1, 2),
			listOf(1, 0, 0),
			listOf(1, 0, 1),
			listOf(1, 0, 2),
			listOf(1, 1, 0),
			listOf(1, 1, 1),
			listOf(1, 1, 2),
			listOf(2, 0, 0),
			listOf(2, 0, 1),
			listOf(2, 0, 2),
			listOf(2, 1, 0),
			listOf(2, 1, 1),
			listOf(2, 1, 2)
		))
	}
	
	@Test
	fun testTranspose() {
		val a = matrixOf(
			rowOfInts(2, 3, 4),
			rowOfInts(1, 0, -1)
		)
		assertThat(a.transpose(), approxEquals(matrixOf(
			rowOfInts(2, 1),
			rowOfInts(3, 0),
			rowOfInts(4, -1)
		)))
	}
	
	@Test
	fun testReshape() {
		val a = vectorOfInts(3, 2, 4, 9, 2, 9)
		val mat = a.reshape(1, 6)
		assertThat(a.reshape(6, 1), approxEquals(mat.transpose()))
		assertThat(a.reshape(2, 3), approxEquals(matrixOf(
			rowOfInts(3, 2, 4),
			rowOfInts(9, 2, 9)
		)))
		a.reshapeAssign(3, 2)
		assertThat(a, approxEquals(matrixOf(
			rowOfInts(3, 2),
			rowOfInts(4, 9),
			rowOfInts(2, 9)
		)))
	}
	
	@Test
	fun testDestructuring() {
		val (a, b, c) = matrix3DOf(
			arrayOf(
				rowOfInts(2, 5, 3),
				rowOfInts(0, 0, 1)
			),
			arrayOf(
				rowOfInts(-1, -13, 79),
				rowOfInts(1, 10, 43)
			),
			arrayOf(
				rowOfInts(1, 1, 2),
				rowOfInts(2, 9, 3)
			)
		)
		val (rowA, rowB) = a
		val (cellA, cellB, cellC) = rowA
		
		assertThat(a, approxEquals(matrixOf(rowOfInts(2, 5, 3), rowOfInts(0, 0, 1))))
		assertThat(b, approxEquals(matrixOf(rowOfInts(-1, -13, 79), rowOfInts(1, 10, 43))))
		assertThat(c, approxEquals(matrixOf(rowOfInts(1, 1, 2), rowOfInts(2, 9, 3))))
		assertThat(rowA, approxEquals(vectorOfInts(2, 5, 3)))
		assertThat(rowB, approxEquals(vectorOfInts(0, 0, 1)))
		assertThat(cellA, approxEquals(scalarOfInt(2)))
		assertThat(cellB, approxEquals(scalarOfInt(5)))
		assertThat(cellC, approxEquals(scalarOfInt(3)))
	}
}
