package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day9Test {
    @ParameterizedTest
    @CsvSource(
        "2199943210%3987894921%9856789892%8767896789%9899965678, 15"
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace("%", "\n")
        assertEquals(expected, Day9(problemInput).runPartOne())
    }

    @ParameterizedTest
    @CsvSource(
        "2199943210%3987894921%9856789892%8767896789%9899965678, 1134"
    )
    fun runPartTwo(input: String, expected: String) {
        val problemInput = input
            .replace("%", "\n")
        assertEquals(expected, Day9(problemInput).runPartTwo())
    }
}