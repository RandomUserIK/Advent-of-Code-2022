import java.util.*

private const val STACK_DISTANCE = 4

data class Directive(
	val numberOfCrates: Int,
	val from: Int,
	val to: Int,
)

fun List<String>.toStacks(): List<Stack<Char>> {
	val numberOfStacks = last().split("  ").size
	val result = List(numberOfStacks) { Stack<Char>() }
	takeWhile { it.contains("[") }.reversed().forEach { crates ->
		crates.forEachIndexed { idx, symbol ->
			if (symbol.isUpperCase())
				result[idx / STACK_DISTANCE].push(symbol)
		}
	}
	return result
}

fun List<String>.toDirectives(): List<Directive> =
	map {
		Directive(
			numberOfCrates = it.substringAfter("move ").substringBefore(" from").toInt(),
			from = it.substringAfter("from ").substringBefore(" to").toInt() - 1,
			to = it.substringAfter("to ").toInt() - 1
		)
	}

fun main() {
	fun getStacksAndDirectives(input: List<String>): Pair<List<Stack<Char>>, List<Directive>> =
		Pair(
			input.takeWhile { it.isNotEmpty() }.toStacks(),
			input.takeLastWhile { it.isNotEmpty() }.toDirectives()
		)

	fun part1(crateStacks: List<Stack<Char>>, directives: List<Directive>) {
		directives.forEach { directive ->
			repeat(directive.numberOfCrates) { crateStacks[directive.to].push(crateStacks[directive.from].pop()) }
		}
		println(crateStacks.map { it.peek() }.joinToString(""))
	}

	fun part2(crateStacks: List<Stack<Char>>, directives: List<Directive>) {
		directives.forEach { directive ->
			val toBeMoved = crateStacks[directive.from].takeLast(directive.numberOfCrates)
			crateStacks[directive.to].addAll(toBeMoved)
			repeat(directive.numberOfCrates) { crateStacks[directive.from].pop() }
		}
		println(crateStacks.map { it.peek() }.joinToString(""))
	}

	val input = readInput("inputs/day05_input")
	val (crateStacks, directives) = getStacksAndDirectives(input)
	part1(crateStacks, directives)
	part2(crateStacks, directives)
}