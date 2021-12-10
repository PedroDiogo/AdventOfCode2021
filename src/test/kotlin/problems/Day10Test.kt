package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day10Test {
    @ParameterizedTest
    @CsvSource(
        "[({(<(())[]>[[{[]{<()<>>%[(()[<>])]({[<{<<[]>>(%{([(<{}[<>[]}>{[]{[(<()>%(((({<>}<{<{<>}{[]{[]{}%[[<[([]))<([[{}[[()]]]%[{[{({}]{}}([{[{{{}}([]%{<[[]]>}<{[{[{[]{()[[[]%[<(<(<(<{}))><([]([]()%<{([([[(<>()){}]>(<<{{%<{([{{}}[<[[[<>{}]]]>[]], 26397"
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace("%", "\n")
        assertEquals(expected, Day10(problemInput).runPartOne())
    }
}

