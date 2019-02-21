package fwcd.miniml.math

/**
 * An inline class that wraps the interface of an
 * IntArray, but does not allow mutations.
 */
inline class ReadOnlyIntArray(private val arr: IntArray) {
	val size: Int
		get() = arr.size
	val indices: IntRange
		get() = arr.indices
	val lastIndex: Int
		get() = arr.lastIndex
	
	operator fun get(index: Int): Int = arr[index]
	
	operator fun iterator(): IntIterator = arr.iterator()
	
	fun all(predicate: (Int) -> Boolean): Boolean = arr.all(predicate)
	
	fun any(): Boolean = arr.any()
	
	fun any(predicate: (Int) -> Boolean): Boolean = arr.any(predicate)
	
	fun asIterable(): Iterable<Int> = arr.asIterable()
	
	fun asSequence(): Sequence<Int> = arr.asSequence()
	
	fun <K, V> associate(transform: (Int) -> Pair<K, V>): Map<K, V> = arr.associate(transform)
	
	fun <K, M : MutableMap<in K, in Int>> associateByTo(destination: M, keySelector: (Int) -> K): M = arr.associateByTo(destination, keySelector)
	
	fun <K, V, M : MutableMap<in K, in V>> associateByTo(destination: M, keySelector: (Int) -> K, valueTransform: (Int) -> V): M = arr.associateByTo(destination, keySelector, valueTransform)
	
	fun <K, V, M : MutableMap<in K, in V>> associateTo(destination: M, transform: (Int) -> Pair<K, V>): M = arr.associateTo(destination, transform)
	
	fun average(): Double = arr.average()
	
	fun binarySearch(element: Int, fromIndex: Int = 0, toIndex: Int = size): Int = arr.binarySearch(element, fromIndex, toIndex)
	
	operator fun component1(): Int = arr[1]
	
	operator fun component2(): Int = arr[2]
	
	operator fun component3(): Int = arr[3]
	
	operator fun component4(): Int = arr[4]
	
	operator fun component5(): Int = arr[5]
	
	operator fun contains(element: Int): Boolean = arr.contains(element)
	
	fun count(): Int = arr.count()
	
	fun count(predicate: (Int) -> Boolean): Int = arr.count(predicate)
	
	fun distinct(): List<Int> = arr.distinct()
	
	fun <K> distinctBy(selector: (Int) -> K): List<Int> = arr.distinctBy(selector)

	fun drop(n: Int): List<Int> = arr.drop(n)

	fun dropLast(n: Int): List<Int> = arr.dropLast(n)

	fun dropLastWhile(predicate: (Int) -> Boolean): List<Int> = arr.dropLastWhile(predicate)

	fun dropWhile(predicate: (Int) -> Boolean): List<Int> = arr.dropWhile(predicate)

	fun elementAt(index: Int): Int = arr.elementAt(index)

	fun elementAtOrElse(index: Int, defaultValue: (Int) -> Int): Int = arr.elementAtOrElse(index, defaultValue)

	fun elementAtOrNull(index: Int): Int? = arr.elementAtOrNull(index)

	fun fill(element: Int, fromIndex: Int = 0, toIndex: Int = size) = arr.fill(element, fromIndex, toIndex)

	fun filter(predicate: (Int) -> Boolean): List<Int> = arr.filter(predicate)

	fun filterIndexed(predicate: (index: Int, Int) -> Boolean): List<Int> = arr.filterIndexed(predicate)

	fun <C : MutableCollection<in Int>> filterIndexedTo(destination: C, predicate: (index: Int, Int) -> Boolean): C = arr.filterIndexedTo(destination, predicate)

	fun filterNot(predicate: (Int) -> Boolean): List<Int> = arr.filterNot(predicate)

	fun <C : MutableCollection<in Int>> filterNotTo(destination: C, predicate: (Int) -> Boolean): C = arr.filterNotTo(destination, predicate)

	fun <C : MutableCollection<in Int>> filterTo(destination: C, predicate: (Int) -> Boolean): C = arr.filterTo(destination, predicate)

	fun find(predicate: (Int) -> Boolean): Int? = arr.find(predicate)

	fun findLast(predicate: (Int) -> Boolean): Int? = arr.findLast(predicate)

	fun first(): Int = arr.first()

	fun first(predicate: (Int) -> Boolean): Int = arr.first(predicate)

	fun firstOrNull(): Int? = arr.firstOrNull()

	fun firstOrNull(predicate: (Int) -> Boolean): Int? = arr.firstOrNull(predicate)

	fun <R> flatMap(transform: (Int) -> Iterable<R>): List<R> = arr.flatMap(transform)

	fun <R, C : MutableCollection<in R>> flatMapTo(destination: C, transform: (Int) -> Iterable<R>): C = arr.flatMapTo(destination, transform)

	fun <R> fold(initial: R, operation: (R, Int) -> R): R = arr.fold(initial, operation)

	fun <R> foldIndexed(initial: R, operation: (Int, R, Int) -> R): R = arr.foldIndexed(initial, operation)

	fun <R> foldRight(initial: R, operation: (Int, R) -> R): R = arr.foldRight(initial, operation)

	fun <R> foldRightIndexed(initial: R, operation: (Int, Int, R) -> R): R = arr.foldRightIndexed(initial, operation)

	fun forEach(action: (Int) -> Unit) = arr.forEach(action)

	fun forEachIndexed(action: (index: Int, Int) -> Unit) = arr.forEachIndexed(action)

	fun getOrElse(index: Int, defaultValue: (Int) -> Int): Int = arr.getOrElse(index, defaultValue)

	fun getOrNull(index: Int): Int? = arr.getOrNull(index)

	fun <K> groupBy(keySelector: (Int) -> K): Map<K, List<Int>> = arr.groupBy(keySelector)

	fun <K, V> groupBy(keySelector: (Int) -> K, valueTransform: (Int) -> V): Map<K, List<V>> = arr.groupBy(keySelector, valueTransform)

	fun <K, M : MutableMap<in K, MutableList<Int>>> groupByTo(destination: M, keySelector: (Int) -> K): M = arr.groupByTo(destination, keySelector)

	fun <K, V, M : MutableMap<in K, MutableList<V>>> groupByTo(destination: M, keySelector: (Int) -> K, valueTransform: (Int) -> V): M = arr.groupByTo(destination, keySelector, valueTransform)

	fun indexOf(element: Int): Int = arr.indexOf(element)

	fun indexOfFirst(predicate: (Int) -> Boolean): Int = arr.indexOfFirst(predicate)

	fun indexOfLast(predicate: (Int) -> Boolean): Int = arr.indexOfLast(predicate)

	infix fun intersect(other: Iterable<Int>): Set<Int> = arr.intersect(other)

	fun isEmpty(): Boolean = arr.isEmpty()

	fun isNotEmpty(): Boolean = arr.isNotEmpty()

	fun <A : Appendable> joinTo(buffer: A, separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...", transform: ((Int) -> CharSequence)? = null): A = arr.joinTo(buffer, separator, prefix, postfix, limit, truncated, transform)

	fun joinToString(separator: CharSequence = ", ", prefix: CharSequence = "", postfix: CharSequence = "", limit: Int = -1, truncated: CharSequence = "...", transform: ((Int) -> CharSequence)? = null): String = arr.joinToString(separator, prefix, postfix, limit, truncated, transform)

	fun last(): Int = arr.last()

	fun last(predicate: (Int) -> Boolean): Int = arr.last(predicate)

	fun lastIndexOf(element: Int): Int = arr.lastIndexOf(element)

	fun lastOrNull(): Int? = arr.lastOrNull()

	fun lastOrNull(predicate: (Int) -> Boolean): Int? = arr.lastOrNull(predicate)

	fun <R> map(transform: (Int) -> R): List<R> = arr.map(transform)

	fun <R> mapIndexed(transform: (index: Int, Int) -> R): List<R> = arr.mapIndexed(transform)

	fun <R, C : MutableCollection<in R>> mapIndexedTo(destination: C, transform: (index: Int, Int) -> R): C = arr.mapIndexedTo(destination, transform)

	fun <R, C : MutableCollection<in R>> mapTo(destination: C, transform: (Int) -> R): C = arr.mapTo(destination, transform)

	fun max(): Int? = arr.max()

	fun <R : Comparable<R>> maxBy(selector: (Int) -> R): Int? = arr.maxBy(selector)

	fun maxWith(comparator: Comparator<in Int>): Int? = arr.maxWith(comparator)

	fun min(): Int? = arr.min()

	fun <R : Comparable<R>> minBy(selector: (Int) -> R): Int? = arr.minBy(selector)

	fun minWith(comparator: Comparator<in Int>): Int? = arr.minWith(comparator)

	fun none(): Boolean = arr.none()

	fun none(predicate: (Int) -> Boolean): Boolean = arr.none(predicate)

	fun partition(predicate: (Int) -> Boolean): Pair<List<Int>, List<Int>> = arr.partition(predicate)

	fun random(): Int = arr.random()
	
	fun reduce(operation: (Int, Int) -> Int): Int = arr.reduce(operation)

	fun reduceIndexed(operation: (Int, Int, Int) -> Int): Int = arr.reduceIndexed(operation)

	fun reduceRight(operation: (Int, Int) -> Int): Int = arr.reduceRight(operation)

	fun reduceRightIndexed(operation: (Int, Int, Int) -> Int): Int = arr.reduceRightIndexed(operation)

	fun reverse() = arr.reverse()

	fun reversed(): List<Int> = arr.reversed()

	fun reversedArray(): IntArray = arr.reversedArray()

	fun single(): Int = arr.single()

	fun single(predicate: (Int) -> Boolean): Int = arr.single(predicate)

	fun singleOrNull(): Int? = arr.singleOrNull()

	fun singleOrNull(predicate: (Int) -> Boolean): Int? = arr.singleOrNull(predicate)

	fun slice(indices: IntRange): List<Int> = arr.slice(indices)

	fun slice(indices: Iterable<Int>): List<Int> = arr.slice(indices)

	fun sliceArray(indices: Collection<Int>): IntArray = arr.sliceArray(indices)

	fun sliceArray(indices: IntRange): IntArray = arr.sliceArray(indices)

	fun sort(fromIndex: Int = 0, toIndex: Int = size) = arr.sort(fromIndex, toIndex)

	fun sortDescending() = arr.sortDescending()

	fun sorted(): List<Int> = arr.sorted()

	fun sortedArray(): IntArray = arr.sortedArray()

	fun sortedArrayDescending(): IntArray = arr.sortedArrayDescending()

	fun <R : Comparable<R>> sortedBy(selector: (Int) -> R?): List<Int> = arr.sortedBy(selector)

	fun <R : Comparable<R>> sortedByDescending(selector: (Int) -> R?): List<Int> = arr.sortedByDescending(selector)

	fun sortedDescending(): List<Int> = arr.sortedDescending()

	fun sortedWith(comparator: Comparator<in Int>): List<Int> = arr.sortedWith(comparator)

	infix fun subtract(other: Iterable<Int>): Set<Int> = arr.subtract(other)

	fun sum(): Int = arr.sum()

	fun sumBy(selector: (Int) -> Int): Int = arr.sumBy(selector)

	fun sumByDouble(selector: (Int) -> Double): Double = arr.sumByDouble(selector)

	fun take(n: Int): List<Int> = arr.take(n)

	fun takeLast(n: Int): List<Int> = arr.takeLast(n)

	fun takeLastWhile(predicate: (Int) -> Boolean): List<Int> = arr.takeLastWhile(predicate)

	fun takeWhile(predicate: (Int) -> Boolean): List<Int> = arr.takeWhile(predicate)

	fun <C : MutableCollection<in Int>> toCollection(destination: C): C = arr.toCollection(destination)

	fun toHashSet(): HashSet<Int> = arr.toHashSet()

	fun toList(): List<Int> = arr.toList()

	fun toMutableList(): MutableList<Int> = arr.toMutableList()

	fun toMutableSet(): MutableSet<Int> = arr.toMutableSet()

	fun toSet(): Set<Int> = arr.toSet()

	infix fun union(other: Iterable<Int>): Set<Int> = arr.union(other)

	fun withIndex(): Iterable<IndexedValue<Int>> = arr.withIndex()

	fun <R, V> zip(other: Array<out R>, transform: (a: Int, b: R) -> V): List<V> = arr.zip(other, transform)

	infix fun <R> zip(other: Iterable<R>): List<Pair<Int, R>> = arr.zip(other)

	fun <R, V> zip(other: Iterable<R>, transform: (a: Int, b: R) -> V): List<V> = arr.zip(other, transform)

	fun <V> zip(other: IntArray, transform: (a: Int, b: Int) -> V): List<V> = arr.zip(other, transform)
}
