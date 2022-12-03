fun main() {

	fun part1(input: List<Pair<Char, Char>>): Int {
		fun symbolPoints(symbol: Char): Int = symbol - 'X' + 1

		fun roundPoints(opponent: Char, myself: Char): Int =
			when (opponent to myself) {
				'B' to 'X', 'C' to 'Y', 'A' to 'Z' -> 0
				'A' to 'X', 'B' to 'Y', 'C' to 'Z' -> 3
				'C' to 'X', 'A' to 'Y', 'B' to 'Z' -> 6
				else -> error("Invalid input!")
			}

		return input.fold(0) { acc, (opponent, myself) ->
			acc + symbolPoints(myself) + roundPoints(opponent, myself)
		}
	}

	fun part2(input: List<Pair<Char, Char>>): Int {
		fun symbolPoints(opponent: Char, myself: Char): Int =
			when (opponent to myself) {
				'A' to 'Y', 'B' to 'X', 'C' to 'Z' -> 1
				'B' to 'Y', 'C' to 'X', 'A' to 'Z' -> 2
				'C' to 'Y', 'A' to 'X', 'B' to 'Z' -> 3
				else -> error("Invalid input!")
			}

		fun roundPoints(action: Char): Int = (action - 'X') * 3

		return input.fold(0) { acc, (opponent, action) ->
			acc + symbolPoints(opponent, action) + roundPoints(action)
		}
	}

	val input = readInput("day02_input").map { line -> Pair(line.first(), line.last()) }
	println(part1(input))
	println(part2(input))
}