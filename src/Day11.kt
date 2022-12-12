import java.io.File

private data class Item(
	val worryLevel: Int,
)

private data class Monkey(
	val index: Int,
	val items: MutableList<Item> = mutableListOf(),
	val operation: (Int) -> Int,
	val test: (Int) -> Int,
	var activity: Int = 0,
)

private fun String.toOperation(): (Int) -> Int =
	split(" ").let {
		when {
			it.first() == "*" -> fun(input: Int) = it.last().toInt() * input
			else -> fun(input: Int) = it.last().toInt() + input
		}
	}

private fun String.toTestFun(): (Int) -> Int =
	split(" ").let {
		fun(input: Int) =
			when (it.first().toInt() % input == 0) {
				true -> it[1].toInt()
				else -> it.last().toInt()
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
			.map { Item(it.toInt()) }
			.toMutableList(),

		operation = substringAfter("new = old ")
			.substringBefore("\n")
			.toOperation(),

		test = (
				substringAfter("divisible by ")
					.substringBefore("\n") + " " + substringAfter("If true: throw to monkey ")
					.substringBefore("\n") + " " + substringAfter("If false: throw to monkey ")
					.substringBefore("\n")
				).toTestFun()
	)

fun main() {
	fun part1(input: List<String>) {
		val monkeys = input.map { it.toMonkey() }

		// TODO
	}

	fun part2(input: List<String>) {
		// TODO
	}

	val input = File("src/inputs/day11_input.txt").readText().split("\n\n")
	println(part1(input))
	println(part2(input))
}