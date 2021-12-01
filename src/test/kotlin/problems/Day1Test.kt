package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day1Test {
    @ParameterizedTest
    @CsvSource(
        "199|200|208|210|200|207|240|269|260|263,7"
    )
    fun runPartOne(input: String, expected: String) {
        assertEquals(expected, Day1(input.replace("|","\n")).runPartOne())
    }

    @ParameterizedTest
    @CsvSource(
        "199|200|208|210|200|207|240|269|260|263,5"
    )
    fun runPartTwo(input: String, expected: String) {
        assertEquals(expected, Day1(input.replace("|","\n")).runPartTwo())
    }
}