package problems

/**
 * Example day
 */
class Day0(override val input: String) : Problem {
    override val number = 0

    override fun runPartOne(): String {
        return input
            .toCharArray()
            .runningFold(0) { acc, character ->
                acc + when (character) {
                    '(' -> 1
                    ')' -> -1
                    else -> 0
                }
            }
            .last()
            .toString()
    }

    override fun runPartTwo(): String {
        return input
            .toCharArray()
            .runningFold(0) { acc, character ->
                acc + when (character) {
                    '(' -> 1
                    ')' -> -1
                    else -> 0
                }
            }
            .indexOfFirst { i -> i == -1 }
            .toString()
    }
}