package problems

class Day10(override val input: String) : Problem {
    override val number: Int = 10
    private val lines: List<Line> = input.lines().map { Line(it) }

    override fun runPartOne(): String {
        return lines
            .mapNotNull { line -> line.firstIllegalCharacter() }
            .sumOf { char -> char.value()!! }
            .toString()
    }

    private fun Char.value(): Int? {
        return mapOf(
            ')' to 3,
            ']' to 57,
            '}' to 1197,
            '>' to 25137
        )[this]
    }

    override fun runPartTwo(): String {
        val scores = lines
            .filter { line -> line.incomplete() }
            .map { line -> line.missingString() }
            .map { missingString -> missingString.score() }

        return scores
            .sorted()[scores.size / 2]
            .toString()
    }

    private fun String.score(): Long {
        val scores = mapOf(
            ')' to 1,
            ']' to 2,
            '}' to 3,
            '>' to 4,
        )
        return this
            .toCharArray()
            .fold(0) { mul, char ->
                mul * 5 + scores[char]!!
            }
    }

    class Line(private val line: String) {
        private val matchingChunks = mapOf(
            '{' to '}',
            '(' to ')',
            '<' to '>',
            '[' to ']'
        )

        fun firstIllegalCharacter(): Char? {
            try {
                openChunks()
            } catch (e: CorruptedLine) {
                return e.lastCharacter
            }
            return null
        }

        fun incomplete() : Boolean {
            return try {
                openChunks().isNotEmpty()
            } catch (e: Exception) {
                false
            }
        }

        fun missingString(): String {
            val openedChunks = openChunks()

            return openedChunks
                .reversed()
                .map { it.oppositeChunk()!! }
                .joinToString(separator = "")
        }

        private fun openChunks(): List<Char> {
            val openedChunks = mutableListOf<Char>()
            line.toCharArray()
                .forEach { char ->
                    if (char.isOpeningChunk()) {
                        openedChunks.add(char)
                    } else {
                        val lastOpenedChunk = openedChunks.removeLast()
                        if (!lastOpenedChunk.matches(char)) {
                            throw CorruptedLine(char, "Corrupted line: $line")
                        }
                    }
                }
            return openedChunks
        }

        private fun Char.matches(other: Char): Boolean {
            return matchingChunks[this] == other
        }

        private fun Char.isOpeningChunk(): Boolean {
            return matchingChunks.keys.any { it == this }
        }

        private fun Char.oppositeChunk(): Char? {
            return matchingChunks[this]
        }

        private class CorruptedLine(val lastCharacter: Char, message: String?) : Exception(message)
    }
}