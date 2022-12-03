fun main() {

	fun part1(input: List<Pair<Char, Char>>): Int {
		val roundPoints = mapOf(
			Pair('B', 'X') to 0,
			Pair('C', 'Y') to 0,
			Pair('A', 'Z') to 0,
			Pair('A', 'X') to 3,
			Pair('B', 'Y') to 3,
			Pair('C', 'Z') to 3,
			Pair('C', 'X') to 6,
			Pair('A', 'Y') to 6,
			Pair('B', 'Z') to 6,
		)

		// In the first part, we only need to resolve the X, Y and Z symbols
		fun symbolPoints(symbol: Char): Int = symbol - 'X' + 1

		fun roundPoints(opponent: Char, myself: Char): Int = roundPoints[opponent to myself] ?: error("Invalid input!")

		return input.fold(0) { acc, (opponent, myself) ->
			acc + symbolPoints(myself) + roundPoints(opponent, myself)
		}
	}

	fun part2(input: List<Pair<Char, Char>>): Int {
		val symbolPoints = mapOf(
			Pair('A', 'Y') to 1,
			Pair('B', 'X') to 1,
			Pair('C', 'Z') to 1,
			Pair('B', 'Y') to 2,
			Pair('C', 'X') to 2,
			Pair('A', 'Z') to 2,
			Pair('C', 'Y') to 3,
			Pair('A', 'X') to 3,
			Pair('B', 'Z') to 3,
		)

		fun symbolPoints(opponent: Char, myself: Char): Int =
			symbolPoints[opponent to myself] ?: error("Invalid input!")

		fun roundPoints(action: Char): Int = (action - 'X') * 3

		return input.fold(0) { acc, (opponent, action) ->
			acc + symbolPoints(opponent, action) + roundPoints(action)
		}
	}

	val input = readInput("day02_input").map { line -> Pair(line.first(), line.last()) }
	println(part1(input))
	println(part2(input))
}