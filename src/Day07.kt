data class Directory(
	val name: String,
	val children: MutableMap<String, Directory> = hashMapOf(),
	var filesSize: Int = 0,
) {
	val dirSize: Int
		get() = filesSize + children.values.sumOf { it.dirSize }
}

fun findDirectories(dir: Directory): List<Directory> =
	dir.children.values.filter { it.dirSize <= 100_000 } + dir.children.values.flatMap { findDirectories(it) }

fun List<String>.toFileSystem(): Directory {
	val callStack = ArrayDeque<Directory>().also { it.add(Directory("/")) }
	forEach { line ->
		when {
			line == "$ ls" -> {}
			line == "$ cd /" -> callStack.removeIf { it.name != "/" }
			line == "$ cd .." -> callStack.removeFirst()
			line.startsWith("dir") -> {}
			line.startsWith("$ cd") -> {
				val currentDir = callStack.first()
				val name = line.substringAfter("$ cd ")
				callStack.addFirst(currentDir.children.getOrPut(name) { Directory(name) })
			}

			else -> {
				callStack.first().filesSize += line.split(" ").first().toInt()
			}
		}
	}
	return callStack.last()
}

fun main() {
	fun part1(input: List<String>) {
		val root = input.toFileSystem()
		println(findDirectories(root).sumOf { it.dirSize })
	}

	fun part2(input: List<String>) {

	}

	val input = readInput("inputs/day07_input")
	println(part1(input))
	println(part2(input))
}