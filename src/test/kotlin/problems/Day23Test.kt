package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day23Test {
    @ParameterizedTest
    @CsvSource(
        "#############%#...........#%###B#C#B#D###%  #A#D#C#A#%  #########, 12521",
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day23(problemInput).runPartOne())
    }

    @ParameterizedTest
    @CsvSource(
        "#############%#...........#%###B#C#B#D###%  #A#D#C#A#%  #########, 44169",
    )
    fun runPartTwo(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day23(problemInput).runPartTwo())
    }
}