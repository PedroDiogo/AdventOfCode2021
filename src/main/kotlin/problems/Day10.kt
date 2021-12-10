package problems

class Day10(override val input: String) : Problem {
    override val number: Int = 10
    val lines: List<Line> = input.lines().map { Line(it) }

    override fun runPartOne(): String {
        val value: Char.() -> Int? = {
            mapOf(
                ')' to 3,
                ']' to 57,
                '}' to 1197,
                '>' to 25137
            )[this]
        }
        return lines
            .mapNotNull { line -> line.firstIllegalCharacter() }
            .sumOf { char -> char.value()!! }
            .toString()
    }

    class Line(val line: String) {

        fun firstIllegalCharacter(): Char? {
            val isOpeningChunk: Char.() -> Boolean = { setOf('{', '(', '<', '[').any { it == this } }
            val matches: Char.(Char) -> Boolean = { other ->
                setOf(setOf('{', '}'), setOf('(', ')'), setOf('<', '>'), setOf('[', ']'))
                    .any { it == setOf(this, other) }
            }

            val openedChunks = mutableListOf<Char>()
            line.toCharArray().forEach { char ->
                if (char.isOpeningChunk()) {
                    openedChunks.add(char)
                } else {
                    val lastOpenedChunk = openedChunks.removeLast()
                    if (!lastOpenedChunk.matches(char)) {
                        return char
                    }
                }
            }
            return null
        }
    }
}