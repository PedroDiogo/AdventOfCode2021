package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day17Test {
    @ParameterizedTest
    @CsvSource(
        "target area: x=20..30; y=-10..-5, 45",
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day17(problemInput).runPartOne())
    }

    @ParameterizedTest
    @CsvSource(
        "target area: x=20..30; y=-10..-5, 112",
    )
    fun runPartTwo(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day17(problemInput).runPartTwo())
    }
}

