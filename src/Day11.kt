import java.io.File

private const val WORRY_LEVEL_DIVISOR = 3
private const val TOP_ACTIVE_MONKEYS = 2

private data class InspectAndPlayResult(
	val worryLevel: Long,
	val throwTo: Int,
)

private data class Monkey(
	val index: Int,
	val items: MutableList<Long> = mutableListOf(),
	val inspectOperation: (Long) -> Long,
	val test: Long,
	val trueMonkey: Int,
	val falseMonkey: Int,
	var activity: Long = 0,
) {
	fun takeTurn(monkeys: List<Monkey>, worryLevelModifier: (Long) -> Long) {
		items.forEach { item ->
			val (worryLevel, throwTo) = inspectAndPlay(item, worryLevelModifier)
			monkeys.first { it.index == throwTo }.items.add(worryLevel)
		}
		activity += items.size
		items.clear()
	}

	override fun toString(): String =
		"Monkey $index: ${items.map { it }.joinToString(", ")}"

	private fun inspectAndPlay(item: Long, worryLevelModifier: (Long) -> Long): InspectAndPlayResult {
		val worryLevel = worryLevelModifier(inspectOperation(item))
		val throwTo = if (worryLevel % test == 0L) trueMonkey else falseMonkey
		return InspectAndPlayResult(worryLevel = worryLevel, throwTo = throwTo)
	}
}

private fun String.toOperation(): (Long) -> Long =
	split(" ").let {
		when {
			it.last() == "old" -> ({ input -> input * input })
			it.first() == "*" -> ({ input -> it.last().toLong() * input })
			else -> ({ input -> it.last().toLong() + input })
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
			.map { it.toLong() }
			.toMutableList(),

		inspectOperation = substringAfter("new = old ")
			.substringBefore("\n")
			.toOperation(),

		test = substringAfter("divisible by ").substringBefore("\n").toLong(),
		trueMonkey = substringAfter("If true: throw to monkey ").substringBefore("\n").toInt(),
		falseMonkey = substringAfter("If false: throw to monkey ").substringBefore("\n").toInt()
	)

fun main() {
	fun part1(input: List<String>) {
		val numberOfRounds = 20
		val monkeys = input.map { it.toMonkey() }
		repeat(numberOfRounds) {
			monkeys.forEach { monkey ->
				monkey.takeTurn(monkeys) { worryLevel -> worryLevel / WORRY_LEVEL_DIVISOR }
			}
		}
		println(
			"Level of monkey business: ${
				monkeys.map { it.activity }.sortedDescending().take(TOP_ACTIVE_MONKEYS).reduce(Long::times)
			}"
		)
	}

	fun part2(input: List<String>) {
		val numberOfRounds = 10_000
		val monkeys = input.map { it.toMonkey() }
		val modulus = monkeys.map { it.test }.reduce(Long::times)
		repeat(numberOfRounds) {
			monkeys.forEach { monkey ->
				monkey.takeTurn(monkeys) { worryLevel -> worryLevel % modulus }
			}
		}
		println(
			"Level of monkey business: ${
				monkeys.map { it.activity }.sortedDescending().take(TOP_ACTIVE_MONKEYS).reduce(Long::times)
			}"
		)
	}

	val input = File("src/inputs/day11_input.txt").readText().split("\n\n")
	part1(input)
	part2(input)
}