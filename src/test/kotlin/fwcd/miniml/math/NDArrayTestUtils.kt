package fwcd.miniml.math

import org.hamcrest.Matcher
import org.hamcrest.BaseMatcher
import org.hamcrest.Description

const val EPS: Double = 0.01

/** Returns a matcher testing for approximate equality with an NDArray. */
fun approxEquals(rhs: NDArray): Matcher<NDArray> {
	return object : BaseMatcher<NDArray>() {
		override fun matches(lhs: Any): Boolean = (lhs as? NDArray)?.equals(rhs, EPS) ?: false
		
		override fun describeTo(description: Description) {
			description
				.appendText("should match ")
				.appendValue(rhs)
		}
	}
}
