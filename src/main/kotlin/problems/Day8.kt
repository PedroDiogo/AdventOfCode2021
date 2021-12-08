package problems

class Day8(override val input: String) : Problem {
    override val number: Int = 8
    private val entries: List<Entry> = input.lines().map { Entry.fromString(it) }

    override fun runPartOne(): String {
        return entries
            .flatMap { entry -> entry.outputValues }
            .count { outputValue -> outputValue.length in 2..4 || outputValue.length == 7 }
            .toString()
    }

    override fun runPartTwo(): String {
        return entries
            .sumOf { entry -> entry.output() }
            .toString()
    }

    private data class Entry(val signalPatterns: List<String>, val outputValues: List<String>) {
        companion object {
            fun fromString(str: String): Entry {
                val (signalPatterns, outputValues) = str.split(" | ")
                return Entry(
                    signalPatterns.split(" "),
                    outputValues.split(" ")
                )
            }
        }

        private enum class Segment {
            A, B, C, D, E, F, G;
        }

        val digits = mapOf(
            1 to signalPatterns.single { pattern -> pattern.length == 2 }.toSet(),
            4 to signalPatterns.single { pattern -> pattern.length == 4 }.toSet(),
            7 to signalPatterns.single { pattern -> pattern.length == 3 }.toSet(),
        )

        private val segmentsToDigit = mapOf(
            setOf(Segment.A, Segment.C, Segment.F, Segment.G, Segment.E, Segment.B) to '0',
            setOf(Segment.C, Segment.F) to '1',
            setOf(Segment.A, Segment.C, Segment.G, Segment.E, Segment.D) to '2',
            setOf(Segment.A, Segment.C, Segment.F, Segment.G, Segment.D) to '3',
            setOf(Segment.C, Segment.F, Segment.B, Segment.D) to '4',
            setOf(Segment.A, Segment.F, Segment.G, Segment.B, Segment.D) to '5',
            setOf(Segment.A, Segment.F, Segment.G, Segment.E, Segment.B, Segment.D) to '6',
            setOf(Segment.A, Segment.C, Segment.F) to '7',
            setOf(Segment.A, Segment.C, Segment.F, Segment.G, Segment.E, Segment.B, Segment.D) to '8',
            setOf(Segment.A, Segment.C, Segment.F, Segment.G, Segment.B, Segment.D) to '9',
        )
        private val hits = signalPatterns
            .flatMap { it.toCharArray().toList() }
            .groupingBy { it }
            .eachCount()

        private val charToSegment: Map<Char, Segment> = mapOf(
            segmentAChar() to Segment.A,
            segmentBChar() to Segment.B,
            segmentCChar() to Segment.C,
            segmentDChar() to Segment.D,
            segmentEChar() to Segment.E,
            segmentFChar() to Segment.F,
            segmentGChar() to Segment.G,
        )

        fun output(): Int {
            return outputValues
                .map { value -> valueToSegments(value) }
                .map { segments -> segmentsToDigit[segments]!! }
                .joinToString(separator = "")
                .toInt()
        }

        private fun valueToSegments(value: String): Set<Segment> {
            return value.toCharArray()
                .map { charToSegment[it]!! }
                .toSet()
        }

        private fun segmentAChar(): Char {
            return (digits[7]!! - digits[1]!!).single()
        }

        private fun segmentBChar(): Char {
            return hits.filterValues { hits -> hits == 6 }
                .keys
                .single()
        }

        private fun segmentCChar(): Char {
            return hits.filter { (char, hits) -> hits == 8 && char != segmentAChar() }
                .keys
                .single()
        }

        private fun segmentDChar(): Char {
            return (digits[4]!! - digits[1]!! - segmentBChar()).single()
        }

        private fun segmentEChar(): Char {
            return hits.filterValues { hits -> hits == 4 }
                .keys
                .single()
        }

        private fun segmentFChar(): Char {
            return hits.filterValues { hits -> hits == 9 }
                .keys
                .single()
        }

        private fun segmentGChar(): Char {
            return hits.filter { (char, hits) -> hits == 7 && char != segmentDChar() }
                .keys
                .single()
        }
    }
}