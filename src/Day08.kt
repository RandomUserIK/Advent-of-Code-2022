fun List<String>.toIntMatrix(): Array<Array<Int>> =
	map { row ->
		row.map(Char::digitToInt).toTypedArray()
	}.toTypedArray()

fun Array<Array<Int>>.neighborsFrom(row: Int, col: Int): List<List<Int>> =
	listOf(
		(0 until row).map { this[it][col] }, // north
		(row + 1..lastIndex).map { this[it][col] }, // south
		this[row].slice(col + 1..lastIndex), // east
		this[row].slice(0 until col) // west
	)

fun Array<Array<Int>>.isVisible(row: Int, col: Int): Boolean =
	neighborsFrom(row, col).any { neighbors ->
		neighbors.all { it < this[row][col] }
	}

fun main() {
	fun part1(input: List<String>): Int {
		var visibleTrees = 4 * input.size - 4 // trees around the edge
		val treeMap = input.toIntMatrix()
		(1 until treeMap.lastIndex).forEach { row ->
			visibleTrees += (1 until treeMap.lastIndex).count { col -> treeMap.isVisible(row, col) }
		}
		return visibleTrees
	}

	fun part2(input: List<String>) {

	}

	val input = readInput("inputs/day08_input")
	println(part1(input))
	println(part2(input))
}