package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day0Test {
    @ParameterizedTest
    @CsvSource(
        "(()), 0",
        "()(), 0",
        "(((, 3",
        "(()(()(, 3",
        "))(((((, 3",
        "()), -1",
        "))(, -1",
        "))), -3",
        ")())()), -3"
    )
    fun runPartOne(input: String, expected: String) {
        assertEquals(expected, Day0(input).runPartOne())
    }

    @ParameterizedTest
    @CsvSource(
        "), 1",
        "()()), 5"
    )
    fun runPartTwo(input: String, expected: String) {
        assertEquals(expected, Day0(input).runPartTwo())
    }
}