fun String.hasAllUniqueChars(): Boolean = toSet().size == length

fun main() {

	fun solve(input: String, windowSize: Int): Int =
		input.windowed(windowSize).indexOfFirst { it.hasAllUniqueChars() } + windowSize

	fun part1(input: String): Int = solve(input, 4)


	fun part2(input: String): Int = solve(input, 14)

	val input = readInput("inputs/day06_input")
	println(part1(input.first()))
	println(part2(input.first()))
}