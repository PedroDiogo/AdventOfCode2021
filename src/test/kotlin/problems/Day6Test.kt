package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day6Test {
    @ParameterizedTest
    @CsvSource(
        "3;4;3;1;2,5934"
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace(";",",")
            .replace("|", "\n")
        assertEquals(expected, Day6(problemInput).runPartOne())
    }

    @ParameterizedTest
    @CsvSource(
        "3;4;3;1;2,26984457539"
    )
    fun runPartTwo(input: String, expected: String) {
        val problemInput = input
            .replace(";",",")
            .replace("|", "\n")
        assertEquals(expected, Day6(problemInput).runPartTwo())
    }
}

