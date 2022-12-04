private fun String.toRanges(): Pair<IntRange, IntRange> =
	substringBefore(",").toRange() to substringAfter(",").toRange()

private fun String.toRange(): IntRange = substringBefore("-").toInt()..substringAfter("-").toInt()

private infix fun IntRange.containsAll(other: IntRange): Boolean =
	first <= other.first && last >= other.last

private infix fun IntRange.overlaps(other: IntRange): Boolean =
	first <= other.last && last >= other.first

fun main() {
	fun part1(input: List<String>): Int =
		input
			.map { it.toRanges() }
			.fold(0) { acc, (left, right) ->
				acc + when {
					left containsAll right || right containsAll left -> 1
					else -> 0
				}
			}

	fun part2(input: List<String>): Int =
		input
			.map { it.toRanges() }
			.fold(0) { acc, (left, right) ->
				acc + when {
					left overlaps right -> 1
					else -> 0
				}
			}

	val input = readInput("inputs/day04_input")
	println(part1(input))
	println(part2(input))
}