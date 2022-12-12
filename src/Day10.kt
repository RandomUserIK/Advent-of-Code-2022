fun main() {
	fun part1(input: List<String>): Int {
		var signalStrength = 0
		var cycle = 1
		var register = 1

		fun calculateSignalStrength(currentCycle: Int, register: Int): Int =
			currentCycle * register

		fun increaseSignalStrength(currentCycle: Int, register: Int) {
			if (currentCycle % 40 == 20)
				signalStrength += calculateSignalStrength(currentCycle, register)
		}

		input.forEach { instruction ->
			++cycle
			increaseSignalStrength(cycle, register)
			if (instruction != "noop") {
				++cycle
				register += instruction.split(" ").last().toInt()
				increaseSignalStrength(cycle, register)
			}
		}
		return signalStrength
	}

	fun part2(input: List<String>) {
		var cycle = 1
		var register = 1

		fun overlapsSprite(position: Int, register: Int): Boolean = position % 40 in (register - 1..register + 1)

		fun getCRTSymbol(position: Int, register: Int): String {
			if (position % 40 == 0)
				println()

			return when {
				overlapsSprite(position, register) -> "#"
				else -> "."
			}
		}

		input.forEach { instruction ->
			print(getCRTSymbol(cycle - 1, register))
			++cycle

			if (instruction != "noop") {
				print(getCRTSymbol(cycle - 1, register))
				++cycle
				register += instruction.split(" ").last().toInt()
			}
		}
	}

	val input = readInput("inputs/day10_input")
	println(part1(input))
	part2(input)
	println()
}