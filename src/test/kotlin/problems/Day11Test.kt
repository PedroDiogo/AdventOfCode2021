package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day11Test {
    @ParameterizedTest
    @CsvSource(
        "5483143223%2745854711%5264556173%6141336146%6357385478%4167524645%2176841721%6882881134%4846848554%5283751526, 1656"
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace("%", "\n")
        assertEquals(expected, Day11(problemInput).runPartOne())
    }

    @ParameterizedTest
    @CsvSource(
        "5483143223%2745854711%5264556173%6141336146%6357385478%4167524645%2176841721%6882881134%4846848554%5283751526, 195"
    )
    fun runPartTwo(input: String, expected: String) {
        val problemInput = input
            .replace("%", "\n")
        assertEquals(expected, Day11(problemInput).runPartTwo())
    }
}

