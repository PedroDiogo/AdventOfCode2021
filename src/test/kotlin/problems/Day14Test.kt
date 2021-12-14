package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day14Test {
    @ParameterizedTest
    @CsvSource(
        "NNCB%%CH -> B%HH -> N%CB -> H%NH -> C%HB -> C%HC -> B%HN -> C%NN -> C%BH -> H%NC -> B%NB -> B%BN -> B%BB -> N%BC -> B%CC -> N%CN -> C, 1588",
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day14(problemInput).runPartOne())
    }
}

