package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day3Test {
    @ParameterizedTest
    @CsvSource(
        "00100|11110|10110|10111|10101|01111|00111|11100|10000|11001|00010|01010,198"
    )
    fun runPartOne(input: String, expected: String) {
        assertEquals(expected, Day3(input.replace("|","\n")).runPartOne())
    }
}

