private const val GROUP_SIZE = 3
private val PRIORITIES = (('a'..'z') + ('A'..'Z')).mapIndexed { idx, c -> c to (idx + 1) }.toMap()


fun main() {
	fun part1(input: List<String>): Int =
		input
			.map {
				val (first, second) = it.chunked(it.length / 2)
				Pair(first.toSet(), second.toSet())
			}
			.fold(0) { acc, (left, right) -> acc + (PRIORITIES[(left intersect right).first()] ?: 0) }

	fun part2(input: List<String>): Int =
		input
			.chunked(GROUP_SIZE)
			.map { (first: String, second: String, third: String) ->
				(first.toSet() intersect second.toSet() intersect third.toSet()).first()
			}
			.fold(0) { acc, badge -> acc + (PRIORITIES[badge] ?: 0) }

	val input = readInput("inputs/day03_input")
	println(part1(input))
	println(part2(input))
}