package problems

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

internal class Day16Test {
    @ParameterizedTest
    @CsvSource(
        "8A004A801A8002F478, 16",
        "620080001611562C8802118E34, 12",
        "C0015000016115A2E0802F182340, 23",
        "A0016C880162017C3686B18A3D4780, 31",
    )
    fun runPartOne(input: String, expected: String) {
        val problemInput = input
            .replace(";", ",")
            .replace("%", "\n")
        assertEquals(expected, Day16(problemInput).runPartOne())
    }

    @Test
    fun literalValueFromString() {
        val input = "D2FE28".convertToBinary()
        val (packet, read) = Day16.BITS.LiteralValue.fromString(input)

        assertEquals(21, read) // Confirm!!!
        assertEquals(6, packet.version)
        assertEquals(4, packet.typeId)
        assertEquals(2021, packet.value)
    }

    @Test
    fun operatorFromStringExample1() {
        val input = "38006F45291200".convertToBinary()
        val (packet, read) = Day16.BITS.Operator.fromString(input)
//        assertEquals(49, read) // Confirm!!!
        assertEquals(1, packet.version)
        assertEquals(6, packet.typeId)
        assertEquals(2, packet.packets.size)
        assertEquals(Day16.BITS.LiteralValue(6, 10), packet.packets[0])
        assertEquals(Day16.BITS.LiteralValue(2, 20), packet.packets[1])
    }

    @Test
    fun operatorFromStringExample2() {
        val input = "EE00D40C823060".convertToBinary()
        val (packet, read) = Day16.BITS.Operator.fromString(input)
        assertEquals(51, read) // Confirm!!!
        assertEquals(7, packet.version)
        assertEquals(3, packet.typeId)
        assertEquals(3, packet.packets.size)
        assertEquals(Day16.BITS.LiteralValue(2, 1), packet.packets[0])
        assertEquals(Day16.BITS.LiteralValue(4, 2), packet.packets[1])
        assertEquals(Day16.BITS.LiteralValue(1, 3), packet.packets[2])
    }

    private fun String.convertToBinary() : String {
        return this
            .toCharArray()
            .joinToString(separator = "") { it.digitToInt(16).toString(2).padStart(4, '0') }
    }
}

