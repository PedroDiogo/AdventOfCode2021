package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day7Test {
    @ParameterizedTest
    @CsvSource(
        "16;1;2;0;4;2;7;1;2;14,37"
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace(";",",")
            .replace("|", "\n")
        assertEquals(expected, Day7(problemInput).runPartOne())
    }
}

