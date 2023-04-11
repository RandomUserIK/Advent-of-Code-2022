private object Parser {
	fun from(input: String): Node =
		from(
			input
				.split("""((?<=[\[\],])|(?=[\[\],]))""".toRegex())
				.filter { it != "," && it.isNotBlank() }
				.iterator()
		)

	fun from(input: Iterator<String>): Node {
		val nodes = mutableListOf<Node>()
		while (input.hasNext()) {
			when (val token = input.next()) {
				"[" -> nodes.add(from(input = input))
				"]" -> return ListNode(nodes = nodes)
				else -> nodes.add(NumberNode(value = token.toInt()))
			}
		}
		return ListNode(nodes = nodes)
	}
}

private abstract class Node : Comparable<Node>

private data class ListNode(val nodes: List<Node>) : Node() {
	override fun compareTo(other: Node): Int =
		when (other) {
			is NumberNode -> compareTo(other.toListNode())
			is ListNode -> nodes
				.zip(other.nodes)
				.map { it.first.compareTo(it.second) }
				.firstOrNull { it != 0 }
				?: nodes.size.compareTo(other.nodes.size)

			else -> throw UnsupportedOperationException("Input is of another unknown Node subtype.")
		}

}

private data class NumberNode(val value: Int) : Node() {
	fun toListNode() = ListNode(nodes = listOf(this))

	override fun compareTo(other: Node): Int =
		when (other) {
			is NumberNode -> value.compareTo(other.value)
			is ListNode -> toListNode().compareTo(other)
			else -> throw UnsupportedOperationException("Input is of another unknown Node subtype.")
		}
}


fun main() {
	fun part1(input: List<String>) =
		input
			.asSequence()
			.filterNot { it.isBlank() }
			.map { Parser.from(it) }
			.chunked(2)
			.mapIndexed { index, pair ->
				if (pair.first() < pair.last()) index + 1 else 0
			}
			.sum()

	fun part2(input: List<String>) {

	}

	val input = readInput("inputs/day13_input")
	println(part1(input))
	println(part2(input))
}