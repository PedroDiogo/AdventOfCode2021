package problems

class Day14(override val input: String) : Problem {
    override val number: Int = 14
    private val polymerTemplate: String = input.split("\n\n").first()
    private val pairInsertionRules: Map<String, Char> = input.split("\n\n")[1]
        .lines()
        .map { line -> line.split(" -> ") }
        .associate { (pair, element) -> pair to element.single() }

    override fun runPartOne(): String {
        val polymerAfter10Steps = (1 .. 10).fold(polymerTemplate) { currentPolymer, _ ->
            currentPolymer
                .zipWithNext()
                .joinToString(separator = "") { (first, second) -> first + (pairInsertionRules[(first + second.toString())]!!).toString() } +
                    currentPolymer.last()
        }
        val hits = polymerAfter10Steps.toCharArray().toList().groupingBy { it }.eachCount()
        return (hits.maxOf { (_, value) -> value } - hits.minOf { (_, value) -> value }).toString()
    }
}