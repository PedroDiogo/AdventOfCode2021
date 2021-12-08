package problems

class Day8(override val input: String) : Problem {
    override val number: Int = 8
    private val entries: List<Entry> = input.lines().map { Entry.fromString(it) }

    override fun runPartOne(): String {
        return entries.flatMap { entry -> entry.outputValues }
            .count { outputValue -> outputValue.length in 2..4 || outputValue.length == 7 }
            .toString()
    }

    private data class Entry(val signalPatterns: List<String>, val outputValues: List<String>) {
        companion object {
            fun fromString(str: String) : Entry {
                val (signalPatterns, outputValues) = str.split("|")
                return Entry(signalPatterns.split(" "), outputValues.split(" "))
            }
        }
    }

}