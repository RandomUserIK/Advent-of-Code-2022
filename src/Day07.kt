data class Directory(
	val name: String,
	val children: MutableMap<String, Directory> = hashMapOf(),
	var filesSize: Int = 0,
) {
	val dirSize: Int
		get() = filesSize + children.values.sumOf { it.dirSize }
}

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
	fun part1(input: List<String>): Int {
		fun findDirectories(dir: Directory): List<Directory> =
			dir.children.values.filter { it.dirSize <= 100000 } + dir.children.values.flatMap { findDirectories(it) }

		val root = input.toFileSystem()
		return findDirectories(root).sumOf { it.dirSize }
	}

	fun part2(input: List<String>): Int {
		fun findDirectories(dir: Directory, diff: Int): List<Directory> =
			dir.children.values.filter { it.dirSize >= diff } + dir.children.values.flatMap {
				findDirectories(
					it,
					diff
				)
			}

		val filesystemSize = 70000000
		val root = input.toFileSystem()
		val unused = filesystemSize - root.dirSize
		val neededSpace = 30000000 - unused
		return findDirectories(root, neededSpace).minOf { it.dirSize }
	}

	val input = readInput("inputs/day07_input")
	println(part1(input))
	println(part2(input))
}