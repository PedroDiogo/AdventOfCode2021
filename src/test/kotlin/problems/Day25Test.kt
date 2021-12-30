package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day25Test {

    @ParameterizedTest
    @CsvSource(
        "v...>>.vv>%.vv>>.vv..%>>.>v>...v%>>v>>.>.v.%v>v.vv.v..%>.>>..v...%.vv..>.>v.%v.v..>>v.v%....v..v.>,58"
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day25(problemInput).runPartOne())
    }

    @ParameterizedTest
    @CsvSource(
        "...>>>>>...,...>>>>.>..",
        "...>>>>.>..,...>>>.>.>.",
        ">>......>.>,>.>......>>",
        "..........%.>v....v..%.......>..%..........,..........%.>........%..v....v>.%..........",
        "...>...%.......%......>%v.....>%......>%.......%..vvv..,..vv>..%.......%>......%v.....>%>......%.......%....v..",
        "..vv>..%.......%>......%v.....>%>......%.......%....v..,....v>.%..vv...%.>.....%......>%v>.....%.......%.......",
        "....v>.%..vv...%.>.....%......>%v>.....%.......%.......,......>%..v.v..%..>v...%>......%..>....%v......%.......",
        "......>%..v.v..%..>v...%>......%..>....%v......%.......,>......%..v....%..>.v..%.>.v...%...>...%.......%v......",
        "v...>>.vv>%.vv>>.vv..%>>.>v>...v%>>v>>.>.v.%v>v.vv.v..%>.>>..v...%.vv..>.>v.%v.v..>>v.v%....v..v.>,....>.>v.>%v.v>.>v.v.%>v>>..>v..%>>v>v>.>.v%.>v.v...v.%v>>.>vvv..%..v...>>..%vv...>>vv.%>.v.v..v.v",
        "....>.>v.>%v.v>.>v.v.%>v>>..>v..%>>v>v>.>.v%.>v.v...v.%v>>.>vvv..%..v...>>..%vv...>>vv.%>.v.v..v.v,>.v.v>>..v%v.v.>>vv..%>v>.>.>.v.%>>v>v.>v>.%.>..v....v%.>v>>.v.v.%v....v>v>.%.vv..>>v..%v>.....vv.", //Done
        ">.v.v>>..v%v.v.>>vv..%>v>.>.>.v.%>>v>v.>v>.%.>..v....v%.>v>>.v.v.%v....v>v>.%.vv..>>v..%v>.....vv.,v>v.v>.>v.%v...>>.v.v%>vv>.>v>..%>>v>v.>.v>%..>....v..%.>.>v>v..v%..v..v>vv>%v.v..>>v..%.v>....v..",
        "v>v.v>.>v.%v...>>.v.v%>vv>.>v>..%>>v>v.>.v>%..>....v..%.>.>v>v..v%..v..v>vv>%v.v..>>v..%.v>....v..,v>..v.>>..%v.v.>.>.v.%>vv.>>.v>v%>>.>..v>.>%..v>v...v.%..>>.>vv..%>.v.vv>v.v%.....>>vv.%vvv>...v..",
        "v>..v.>>..%v.v.>.>.v.%>vv.>>.v>v%>>.>..v>.>%..v>v...v.%..>>.>vv..%>.v.vv>v.v%.....>>vv.%vvv>...v..,vv>...>v>.%v.v.v>.>v.%>.v.>.>.>v%>v>.>..v>>%..v>v.v...%..>.>>vvv.%.>...v>v..%..v.v>>v.v%v.v.>...v."
    )
    fun runStep(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        val expectedOutput = expected
            .replace(";", ",")
            .replace("%", "\n")
        val runStepInput = problemInput
            .lines()
            .map { it.toCharArray() }
        assertEquals(
            expectedOutput,
            Day25(problemInput).runStep(runStepInput)
                .joinToString(separator = "\n") { it.joinToString(separator = "") })
    }
}

