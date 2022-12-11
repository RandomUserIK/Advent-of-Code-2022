fun List<String>.toIntMatrix(): Array<Array<Int>> =
	map { row ->
		row.map(Char::digitToInt).toTypedArray()
	}.toTypedArray()

fun Array<Array<Int>>.neighborsFrom(row: Int, col: Int): List<List<Int>> =
	listOf(
		(0 until row).map { this[it][col] }.asReversed(), // north
		(row + 1..lastIndex).map { this[it][col] }, // south
		this[row].slice(col + 1..lastIndex), // east
		this[row].slice(0 until col).asReversed() // west
	)

fun Array<Array<Int>>.isVisible(row: Int, col: Int): Boolean =
	neighborsFrom(row, col).any { neighbors ->
		neighbors.all { it < this[row][col] }
	}

fun List<Int>.product(): Int =
	reduce { acc, i -> acc * i }

fun List<Int>.numberOfSmallerTrees(referenceHeight: Int): Int {
	var result = 0
	for (item in this) {
		++result
		if (item >= referenceHeight)
			break
	}
	return result
}

fun Array<Array<Int>>.scenicScoreFrom(row: Int, col: Int): Int =
	neighborsFrom(row, col)
		.map { neighbors -> neighbors.numberOfSmallerTrees(this[row][col]) }
		.product()

fun main() {
	fun part1(input: List<String>): Int {
		var visibleTrees = 4 * input.size - 4 // trees around the edge
		val treeMap = input.toIntMatrix()
		(1 until treeMap.lastIndex).forEach { row ->
			visibleTrees += (1 until treeMap.lastIndex).count { col -> treeMap.isVisible(row, col) }
		}
		return visibleTrees
	}

	fun part2(input: List<String>): Int {
		val treeMap = input.toIntMatrix()
		return (1 until treeMap.lastIndex).maxOf { row ->
			(1 until treeMap.lastIndex).maxOf { col -> treeMap.scenicScoreFrom(row, col) }
		}
	}

	val input = readInput("inputs/day08_input")
	println(part1(input))
	println(part2(input))
}