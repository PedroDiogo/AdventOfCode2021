package problems

class Day14(override val input: String) : Problem {
    override val number: Int = 14
    private val polymerTemplate: String = input.split("\n\n").first()
    private val pairInsertionRules: Map<String, Char> = input.split("\n\n")[1]
        .lines()
        .map { line -> line.split(" -> ") }
        .associate { (pair, element) -> pair to element.single() }

    override fun runPartOne(): String {
        return runProcess(10).toString()
    }

    override fun runPartTwo(): String {
        return runProcess(40).toString()
    }

    private fun runProcess(steps: Int): Long {
        val finalPairs = (1..steps).fold(pairs(polymerTemplate)) { currentPairs, _ ->
            runStep(currentPairs)
        }
        val charHits = characterHits(finalPairs)
            .map { it.value }

        return charHits.maxOf { it } - charHits.minOf { it }
    }

    private fun pairs(polymer: String): Map<String, Long> {
        return polymer
            .zipWithNext()
            .map { (p1, p2) -> p1 + p2 }
            .groupingBy { it }
            .eachCount()
            .mapValues { it.value.toLong() }
    }

    private fun runStep(pairs: Map<String, Long>): Map<String, Long> {
        return pairs
            .flatMap { (pair, hits) ->
                listOf(
                    (pair.first() + pairInsertionRules[pair]!!) to hits,
                    (pairInsertionRules[pair]!! + pair.last()) to hits
                )
            }.groupBy { it.first }
            .mapValues { (_, values) -> values.sumOf { (_, hits) -> hits } }
    }

    private fun characterHits(pairs: Map<String, Long>): Map<Char, Long> {
        return pairs
            .flatMap { (entry, value) ->
                listOf(
                    entry[0] to value,
                    entry[1] to value
                )
            }
            .plus(polymerTemplate.first() to 1L)
            .plus(polymerTemplate.last() to 1L)
            .groupBy { it.first }
            .mapValues { (_, values) -> values.sumOf { (_, hits) -> hits } / 2 }
    }

    private operator fun Char.plus(other: Char): String {
        return this.plus(other.toString())
    }
}