package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day15Test {
    @ParameterizedTest
    @CsvSource(
        "1163751742%1381373672%2136511328%3694931569%7463417111%1319128137%1359912421%3125421639%1293138521%2311944581, 40",
        "19999%19111%19191%19191%11191, 14",
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day15(problemInput).runPartOne())
    }

    @ParameterizedTest
    @CsvSource(
        "1163751742%1381373672%2136511328%3694931569%7463417111%1319128137%1359912421%3125421639%1293138521%2311944581, 315",
    )
    fun runPartTwo(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day15(problemInput).runPartTwo())
    }
}

