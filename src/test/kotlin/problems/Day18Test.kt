package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day18Test {
    @ParameterizedTest
    @CsvSource(
        "[[[0;[4;5]];[0;0]];[[[4;5];[2;6]];[9;5]]]%[7;[[[3;7];[4;3]];[[6;3];[8;8]]]]%[[2;[[0;8];[3;4]]];[[[6;7];1];[7;[1;6]]]]%[[[[2;4];7];[6;[0;5]]];[[[6;8];[2;8]];[[2;1];[4;5]]]]%[7;[5;[[3;8];[1;4]]]]%[[2;[2;2]];[8;[8;1]]]%[2;9]%[1;[[[9;3];9];[[9;0];[0;7]]]]%[[[5;[7;4]];7];1]%[[[[4;2];2];6];[8;7]], 3488",
        "[[[0;[5;8]];[[1;7];[9;6]]];[[4;[1;2]];[[1;4];2]]]%[[[5;[2;8]];4];[5;[[9;9];0]]]%[6;[[[6;2];[5;6]];[[7;6];[4;7]]]]%[[[6;[0;7]];[0;9]];[4;[9;[9;0]]]]%[[[7;[6;4]];[3;[1;3]]];[[[5;5];1];9]]%[[6;[[7;3];[3;2]]];[[[3;8];[5;7]];4]]%[[[[5;4];[7;7]];8];[[8;3];8]]%[[9;3];[[9;9];[6;[4;9]]]]%[[2;[[7;7];7]];[[5;8];[[9;3];[0;2]]]]%[[[[5;2];5];[8;[3;7]]];[[5;[7;5]];[4;4]]], 4140"
    )

    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day18(problemInput).runPartOne())
    }

    @Test
    fun snailfishNumberFromStringSimple() {
        val snailfishNumber = Day18.SnailfishNumber.fromString("[1,2]")

        assertEquals(Day18.SnailfishElement(1), snailfishNumber.left)
        assertEquals(Day18.SnailfishElement(2), snailfishNumber.right)
    }

    @Test
    fun snailfishNumberFromStringLeftIsSnailfishNumber() {
        val snailfishNumber = Day18.SnailfishNumber.fromString("[[1,2],3]")

        assertEquals(
            Day18.SnailfishElement(
                element = Day18.SnailfishNumber(
                    Day18.SnailfishElement(1),
                    Day18.SnailfishElement(2)
                )
            ), snailfishNumber.left
        )
        assertEquals(Day18.SnailfishElement(3), snailfishNumber.right)
    }

    @Test
    fun snailfishAdd() {
        val left = Day18.SnailfishNumber.fromString("[1,2]")
        val right = Day18.SnailfishNumber.fromString("[[3,4],5]")
        val expected = Day18.SnailfishNumber.fromString("[[1,2],[[3,4],5]]")

        assertEquals(expected, left + right)
    }

    @ParameterizedTest
    @CsvSource(
        "[9;1], 29",
        "[[9;1];[1;9]], 129",
        "[[1;2];[[3;4];5]], 143",
        "[[[[0;7];4];[[7;8];[6;0]]];[8;1]], 1384",
        "[[[[1;1];[2;2]];[3;3]];[4;4]], 445",
        "[[[[3;0];[5;3]];[4;4]];[5;5]], 791",
        "[[[[5;0];[7;4]];[5;5]];[6;6]], 1137",
        "[[[[8;7];[7;7]];[[8;6];[7;7]]];[[[0;7];[6;6]];[8;7]]], 3488"
    )
    fun magnitude(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected.toLong(), Day18.SnailfishNumber.fromString(problemInput).magnitude())
    }

    @ParameterizedTest
    @CsvSource(
        "[[[[[9;8];1];2];3];4], [[[[0;9];2];3];4]",
        "[7;[6;[5;[4;[3;2]]]]], [7;[6;[5;[7;0]]]]",
        "[[6;[5;[4;[3;2]]]];1], [[6;[5;[7;0]]];3]",
        "[[3;[2;[1;[7;3]]]];[6;[5;[4;3]]]], [[3;[2;[8;0]]];[9;[5;[4;3]]]]",
        "[[3;[2;[[7;3];1]]];[6;[5;[4;3]]]], [[3;[9;[0;4]]];[6;[5;[4;3]]]]",
        "[[3;[2;[1;[7;3]]]];[6;[5;[4;[3;2]]]]], [[3;[2;[8;0]]];[9;[5;[4;[3;2]]]]]",
        "[[[[12;12];[6;14]];[[15;0];[17;[8;1]]]];[2;9]], [[[[12;12];[6;14]];[[15;0];[25;0]]];[3;9]]"
    )
    fun explode(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
        val problemExpected = expected
            .replace(";", ",")
        assertEquals(
            Day18.SnailfishNumber.fromString(problemExpected),
            Day18.SnailfishNumber.fromString(problemInput).explode()
        )

    }

    @Test
    fun split() {
        val numberOne = Day18.SnailfishNumber.fromString("[[[[0,7],4],[15,[0,13]]],[1,1]]")
        val expectedOne = Day18.SnailfishNumber.fromString("[[[[0,7],4],[[7,8],[0,13]]],[1,1]]")
        val expectedTwo = Day18.SnailfishNumber.fromString("[[[[0,7],4],[[7,8],[0,[6,7]]]],[1,1]]")

        assertEquals(expectedOne, numberOne.split())
        assertEquals(expectedTwo, expectedOne.split())
    }

    @Test
    fun reduce() {
        val number = Day18.SnailfishNumber.fromString("[[[[[4,3],4],4],[7,[[8,4],9]]],[1,1]]")
        val expected = Day18.SnailfishNumber.fromString("[[[[0,7],4],[[7,8],[6,0]]],[8,1]]")
        assertEquals(expected, number.reduce())
    }
}

