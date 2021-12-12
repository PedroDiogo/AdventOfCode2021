package problems

class Day12(override val input: String) : Problem {
    override val number: Int = 12

    private val START = "start"
    private val END = "end"
    private val connections: Map<String, Set<String>> = input.lines()
        .map { line -> line.split("-") }
        .flatMap { (a, b) -> listOf(Pair(a, b), Pair(b, a)) } // A connects to B and B connects to A
        .filter { (_, b) -> b != START }                      // Don't want to go back to START
        .groupBy { it.first }
        .mapValues { (_, value) -> value.map { it.second }.toSet() }


    override fun runPartOne(): String {
        return allPathsToEnd(START, visitedSmallCaveTwice = true)
            .count()
            .toString()
    }

    override fun runPartTwo(): String {
        return allPathsToEnd(START)
            .count()
            .toString()
    }

    private fun allPathsToEnd(
        startNode: String,
        currentPath: List<String> = emptyList(),
        visited: Set<String> = emptySet(),
        visitedSmallCaveTwice: Boolean = false
    ): Set<List<String>> {
        val newCurrentPath = currentPath + startNode
        val newVisited = when (startNode) {
            startNode.lowercase() -> visited + startNode
            else -> visited
        }
        if (startNode == END) {
            return setOf(newCurrentPath)
        }

        return connections[startNode]!!.flatMap { connection ->
            if (!visited.contains(connection)) {
                allPathsToEnd(connection, newCurrentPath, newVisited, visitedSmallCaveTwice)
            } else if (!visitedSmallCaveTwice) {
                allPathsToEnd(connection, newCurrentPath, newVisited, true)
            } else {
                emptySet()
            }
        }.toSet()
    }
}