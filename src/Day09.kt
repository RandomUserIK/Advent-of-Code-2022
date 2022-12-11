import kotlin.math.abs

private fun List<String>.toDirections(): List<Direction> =
	joinToString("") {
		val direction = it.substringBefore(" ")
		val steps = it.substringAfter(" ").toInt()
		direction.repeat(steps)
	}.map {
		when (it) {
			'U' -> Direction.U
			'D' -> Direction.D
			'L' -> Direction.L
			'R' -> Direction.R
			else -> error("Invalid direction entry provided: [$it].")
		}
	}


private infix fun Point.touches(tail: Point): Boolean =
	abs(this.row - tail.row) <= 1 && abs(this.col - tail.col) <= 1


private infix fun Point.chase(head: Point): Point =
	(head - this).let { diff ->
		this + Point(row = diff.row.coerceIn(-1, 1), col = diff.col.coerceIn(-1, 1))
	}


private enum class Direction(
	val dRow: Int,
	val dCol: Int,
) {
	U(-1, 0),
	D(1, 0),
	L(0, -1),
	R(0, 1)
}

private data class Point(
	val row: Int = 0,
	val col: Int = 0,
) {
	fun move(direction: Direction): Point = copy(row = row + direction.dRow, col = col + direction.dCol)

	operator fun plus(other: Point) =
		Point(
			row = row + other.row,
			col = col + other.col,
		)

	operator fun minus(other: Point) =
		Point(
			row = row - other.row,
			col = col - other.col,
		)
}


fun main() {
	fun part1(input: List<String>): Int {
		val directions = input.toDirections()
		var head = Point()
		var tail = Point()
		val pointsVisited = mutableSetOf(Point()) // we ought to include the starting point

		directions.forEach { direction ->
			head = head.move(direction)
			if (!(head touches tail)) {
				tail = tail chase head
			}
			pointsVisited.add(tail)
		}
		return pointsVisited.size
	}

	fun part2(input: List<String>): Int {
		val directions = input.toDirections()
		val pointsVisited = mutableSetOf(Point())
		val knots = Array(10) { Point() }

		directions.forEach { direction ->
			knots[0] = knots.first().move(direction)
			(0 until knots.lastIndex).forEach { headIdx ->
				if (!(knots[headIdx] touches knots[headIdx + 1])) {
					knots[headIdx + 1] = knots[headIdx + 1] chase knots[headIdx]
				}
			}
			pointsVisited += knots.last()
		}
		return pointsVisited.size
	}

	val input = readInput("inputs/day09_input")
	println(part1(input))
	println(part2(input))
}