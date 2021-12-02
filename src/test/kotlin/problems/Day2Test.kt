package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day2Test {
    @ParameterizedTest
    @CsvSource(
        "forward 5|down 5|forward 8|up 3|down 8|forward 2,150"
    )
    fun runPartOne(input: String, expected: String) {
        assertEquals(expected, Day2(input.replace("|","\n")).runPartOne())
    }
}
