fun main() {
	fun getElfCalories(input: List<String>): List<Int> {
		var calories = 0
		val elfCalories = mutableListOf<Int>() // priorityQueue better
		input.forEach { cal ->
			when {
				cal.isNotBlank() -> calories += cal.toInt()
				else -> {
					elfCalories.add(calories)
					calories = 0
				}
			}
		}
		return elfCalories.toList()
	}

	fun part1(input: List<String>): Int = getElfCalories(input).max()

	fun part2(input: List<String>): Int = getElfCalories(input).sortedDescending().take(3).sum()


	val input = readInput("inputs/day01_input")
	println(part1(input))
	println(part2(input))
}
