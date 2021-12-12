package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day12Test {
    @ParameterizedTest
    @CsvSource(
        "start-A%start-b%A-c%A-b%b-d%A-end%b-end,10",
        "dc-end%HN-start%start-kj%dc-start%dc-HN%LN-dc%HN-end%kj-sa%kj-HN%kj-dc, 19",
        "fs-end%he-DX%fs-he%start-DX%pj-DX%end-zg%zg-sl%zg-pj%pj-he%RW-he%fs-DX%pj-RW%zg-RW%start-pj%he-WI%zg-he%pj-fs%start-RW, 226"
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace("%", "\n")
        assertEquals(expected, Day12(problemInput).runPartOne())
    }
}

