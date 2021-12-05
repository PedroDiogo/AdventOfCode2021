package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day5Test {
    @ParameterizedTest
    @CsvSource(
        "0;9 -> 5;9|8;0 -> 0;8|9;4 -> 3;4|2;2 -> 2;1|7;0 -> 7;4|6;4 -> 2;0|0;9 -> 2;9|3;4 -> 1;4|0;0 -> 8;8|5;5 -> 8;2,5"
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace(";",",")
            .replace("|", "\n")
        assertEquals(expected, Day5(problemInput).runPartOne())
    }

    @ParameterizedTest
    @CsvSource(
        "0;9 -> 5;9|8;0 -> 0;8|9;4 -> 3;4|2;2 -> 2;1|7;0 -> 7;4|6;4 -> 2;0|0;9 -> 2;9|3;4 -> 1;4|0;0 -> 8;8|5;5 -> 8;2,12"
    )
    fun runPartTwo(input: String, expected: String) {
        val problemInput = input
            .replace(";",",")
            .replace("|", "\n")
        assertEquals(expected, Day5(problemInput).runPartTwo())
    }
}

