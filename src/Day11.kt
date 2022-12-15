import java.io.File

private const val WORRY_LEVEL_DIVISOR = 3
private const val NUMBER_OF_ROUNDS = 20

private data class Monkey(
	val index: Int,
	val items: MutableList<Int> = mutableListOf(),
	val inspectOperation: (Int) -> Int,
	val test: Int,
	val trueMonkey: Int,
	val falseMonkey: Int,
	var activity: Int = 0,
) {
	fun takeTurn(monkeys: List<Monkey>) {
		items.forEach { item ->
			val (throwTo, newItem) = inspectAndPlay(item)
			monkeys[throwTo].items.add(newItem)
		}
		activity += items.size
		items.clear()
	}

	override fun toString(): String =
		"Monkey $index: ${items.map { it }.joinToString(", ")}"

	private fun inspectAndPlay(item: Int): Pair<Int, Int> {
		val newItem = inspectOperation(item) / WORRY_LEVEL_DIVISOR
		val throwTo = if (newItem % test == 0) trueMonkey else falseMonkey
		return Pair(throwTo, newItem)
	}
}

private fun String.toOperation(): (Int) -> Int =
	split(" ").let {
		when {
			it.last() == "old" -> ({ input -> input * input })
			it.first() == "*" -> ({ input -> it.last().toInt() * input })
			else -> ({ input -> it.last().toInt() + input })
		}
	}

private fun String.toMonkey() =
	Monkey(
		index = substringAfter("Monkey ")
			.substringBefore(":")
			.toInt(),

		items = substringAfter("Starting items: ")
			.substringBefore("\n")
			.split(", ")
			.map { it.toInt() }
			.toMutableList(),

		inspectOperation = substringAfter("new = old ")
			.substringBefore("\n")
			.toOperation(),

		test = substringAfter("divisible by ").substringBefore("\n").toInt(),
		trueMonkey = substringAfter("If true: throw to monkey ").substringBefore("\n").toInt(),
		falseMonkey = substringAfter("If false: throw to monkey ").substringBefore("\n").toInt()
	)

fun main() {
	fun part1(input: List<String>) {
		val monkeys = input.map { it.toMonkey() }
		repeat(NUMBER_OF_ROUNDS) { monkeys.forEach { it.takeTurn(monkeys) } }
		val sorted = monkeys.sortedByDescending { it.activity }
		println("Level of monkey business: ${sorted.first().activity * sorted[1].activity}")
	}

	fun part2(input: List<String>) {
		// TODO
	}

	val input = File("src/inputs/day11_input.txt").readText().split("\n\n")
	part1(input)
}