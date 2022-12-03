private const val GROUP_SIZE = 3
private val PRIORITIES = (('a'..'z') + ('A'..'Z')).mapIndexed { idx, c -> c to (idx + 1) }.toMap()


fun main() {
	fun part1(input: List<String>): Int =
		input
			.map {
				val (first, second) = it.chunked(it.length / 2)
				Pair(first, second)
			}
			.fold(0) { acc, pair -> acc + (PRIORITIES[pair.first.firstOrNull { it in pair.second }] ?: 0) }

	fun part2(input: List<String>): Int =
		input
			.chunked(GROUP_SIZE)
			.map { items -> items.first().firstOrNull { badge -> badge in items[1] && badge in items.last() } }
			.fold(0) { acc, badge -> acc + (PRIORITIES[badge] ?: 0) }

	val input = readInput("inputs/day03_input")
	println(part1(input))
	println(part2(input))
}