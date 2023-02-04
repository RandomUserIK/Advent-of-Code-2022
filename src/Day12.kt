private const val START_SYMBOL = 'S'
private const val END_SYMBOL = 'E'

private class Heightmap(
	val start: Point2D,
	val end: Point2D,
	val heights: Map<Point2D, Int>,
) {
	fun shortestPath(from: Point2D, isGoal: (Point2D) -> Boolean, canMoveToward: (Int, Int) -> Boolean): Int {
		val visited = mutableSetOf<Point2D>()
		val steps = ArrayDeque<Step>().also { it.add(Step(from)) }

		while (steps.isNotEmpty()) {
			val currentStep = steps.removeFirst()

			if (isGoal(currentStep.from))
				return currentStep.distance

			currentStep.from.adjacentPoints
				.filter { it !in visited && it in heights }
				.filter { to -> canMoveToward(heights.getValue(currentStep.from), heights.getValue(to)) }
				.forEach {
					steps.add(currentStep.toward(it))
					visited.add(it)
				}
		}

		throw IllegalStateException("Could not find the shortest path.")
	}
}

private data class Point2D(
	val x: Int,
	val y: Int,
) {
	val adjacentPoints: Set<Point2D>
		get() =
			setOf(
				copy(x = x - 1, y = y),
				copy(x = x + 1, y = y),
				copy(x = x, y = y - 1),
				copy(x = x, y = y + 1),
			)
}

private data class Step(val from: Point2D, val distance: Int = 0) {
	fun toward(other: Point2D) = copy(from = other, distance = distance + 1)
}

private fun List<String>.toHeightmap(): Heightmap {
	var start: Point2D? = null
	var end: Point2D? = null
	val heights: MutableMap<Point2D, Int> = mutableMapOf()

	mapIndexed { y, row ->
		row.mapIndexed { x, c ->
			val current = Point2D(x, y)
			heights.put(
				current,
				when (c) {
					START_SYMBOL -> 0.also { start = current }
					END_SYMBOL -> 25.also { end = current }
					else -> c - 'a'
				}
			)
		}
	}

	requireNotNull(start) { "Starting point not found!" }
	requireNotNull(end) { "End point not found!" }
	return Heightmap(
		start = start!!,
		end = end!!,
		heights = heights.toMap(),
	)
}

fun main() {

	fun part1(heightmap: Heightmap): Int =
		heightmap.shortestPath(
			from = heightmap.start,
			isGoal = { it == heightmap.end },
			canMoveToward = { from, to -> to - from <= 1 }
		)

	fun part2(heightmap: Heightmap): Int =
		heightmap.shortestPath(
			from = heightmap.end,
			isGoal = { heightmap.heights[it] == 0 },
			canMoveToward = { from, to -> from - to <= 1 }
		)

	val input = readInput("inputs/day12_input")
	println(part1(input.toHeightmap()))
	println(part2(input.toHeightmap()))
}