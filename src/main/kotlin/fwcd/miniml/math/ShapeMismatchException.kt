package fwcd.miniml.math

class ShapeMismatchException(msg: String) : RuntimeException(msg) {
	constructor(expectedShape: IntArray, actualShape: IntArray)
		: this("Expected shape ${expectedShape.contentToString()} but was ${actualShape.contentToString()}")
	
	constructor(msg: String, expectedShape: IntArray, actualShape: IntArray)
		: this("$msg: Expected shape ${expectedShape.contentToString()} but was ${actualShape.contentToString()}")
		
	constructor(expectedRank: Int, actualRank: Int)
		: this("Expected rank $expectedRank but was $actualRank")
	
	constructor(msg: String, expectedRank: Int, actualRank: Int)
		: this("$msg: Expected rank $expectedRank but was $actualRank")
}
