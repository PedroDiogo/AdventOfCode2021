package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day13Test {
    @ParameterizedTest
    @CsvSource(
        "6;10%0;14%9;10%0;3%10;4%4;11%6;0%6;12%4;1%0;13%10;12%3;4%3;0%8;4%1;10%2;14%8;10%9;0%%fold along y=7%fold along x=5, 17",
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day13(problemInput).runPartOne())
    }

    @ParameterizedTest
    @CsvSource(
        "6;10%0;14%9;10%0;3%10;4%4;11%6;0%6;12%4;1%0;13%10;12%3;4%3;0%8;4%1;10%2;14%8;10%9;0%%fold along y=7%fold along x=5,█████%█   █%█   █%█   █%█████",
    )
    fun runPartTwo(input: String, expectedInline: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        val expected = expectedInline
            .replace("%", "\n")
        assertEquals(expected, Day13(problemInput).runPartTwo())
    }
}