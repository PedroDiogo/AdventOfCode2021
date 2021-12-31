package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day21Test {
    @ParameterizedTest
    @CsvSource(
        "Player 1 starting position: 4%Player 2 starting position: 8, 739785"
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day21(problemInput).runPartOne())
    }
    @ParameterizedTest
    @CsvSource(
        "Player 1 starting position: 4%Player 2 starting position: 8, 444356092776315"
    )
    fun runPartTwo(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day21(problemInput).runPartTwo())
    }
}

