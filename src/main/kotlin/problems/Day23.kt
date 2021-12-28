package problems

import kotlin.math.abs

class Day23(override val input: String) : Problem {
    override val number: Int = 23

    override fun runPartOne(): String {
        val burrow = Burrow.fromString(input)
        return findMinimumComplete(burrow)
            .toString()
    }

    override fun runPartTwo(): String {
        val newLines = """  #D#C#B#A#
  #D#B#A#C#"""
        val modifiedInput = (input.lines().subList(0, 3) + newLines.lines() + input.lines()
            .subList(3, input.lines().size)).joinToString(separator = "\n")

        val burrow = Burrow.fromString(modifiedInput)
        return findMinimumComplete(burrow)
            .toString()
    }

    fun findMinimumComplete(burrow: Burrow, cost: Long = 0L): Long? {
        if (burrow.complete()) {
            return cost
        }
        val availableMoves = burrow.availableMoves()
        if (availableMoves.isEmpty()) {
            return null
        }

        return availableMoves
            .mapNotNull { (burrow, c) -> findMinimumComplete(burrow, cost + c) }
            .minOrNull()
    }

    data class Burrow(val rooms: List<List<Char>>, val hallway: List<Char>) {
        val EMPTY = '.'

        companion object {
            fun fromString(input: String): Burrow {
                val rooms = input.lines()
                    .drop(2)
                    .dropLast(1)
                    .map { line -> listOf(line[3], line[5], line[7], line[9]) }
                    .fold(List(4) { mutableListOf<Char>() }) { rooms, line ->
                        (0..3).forEach { i ->
                            rooms[i].add(0, line[i])
                        }
                        rooms
                    }
                val hallway = input
                    .lines()[1]
                    .toCharArray()
                    .drop(1)
                    .dropLast(1)

                return Burrow(rooms, hallway)
            }
        }

        fun availableMoves(): List<Pair<Burrow, Long>> {
            val movableColumns = (0..3).mapNotNull {
                if (movableColumn(it) != null) {
                    Pair(it, movableColumn(it)!!)
                } else {
                    null
                }
            }

            val movesFromColumnsToColumns = movableColumns
                .flatMap { (columnIdx, positionIdx) -> availableMovesFromColumnToColumn(columnIdx, positionIdx) }
            if (movesFromColumnsToColumns.isNotEmpty()) {
                return movesFromColumnsToColumns
            }

            val movesFromHallway = hallway
                .mapIndexed { index, c -> Pair(index, c) }
                .filter { (_, c) -> c != EMPTY }
                .flatMap { availableMovesFromHallwayToColumn(it.first) }

            if (movesFromHallway.isNotEmpty()) {
                return movesFromHallway
            }

            val movesFromColumnsToHallway = movableColumns
                .flatMap { (columnIdx, positionIdx) -> availableMovesFromColumnToHallway(columnIdx, positionIdx) }
            if (movesFromColumnsToHallway.isNotEmpty()) {
                return movesFromColumnsToHallway
            }

            return emptyList()
        }

        private fun availableMovesFromColumnToColumn(columnIdx: Int, positionIdx: Int): List<Pair<Burrow, Long>> {
            val movesToHallway = rooms[columnIdx].size - positionIdx

            val char = rooms[columnIdx][positionIdx]
            val destinyColumnIdx = char - 'A'
            val destinyHallwayIdx = 2 + 2 * destinyColumnIdx
            val currentHallwayIdx = 2 + 2 * columnIdx

            if (destinyHallwayIdx != currentHallwayIdx && !complete(destinyColumnIdx) && canPutInto(destinyColumnIdx)) {
                val range = when (currentHallwayIdx > destinyHallwayIdx) {
                    true -> currentHallwayIdx downTo destinyHallwayIdx
                    false -> currentHallwayIdx..destinyHallwayIdx
                }
                val canMoveToDestinyColumn = range.all { hallway[it] == EMPTY }
                if (canMoveToDestinyColumn) {
                    val firstEmpty = firstEmpty(destinyColumnIdx)
                    val updatedRooms = rooms.map { it.toMutableList() }
                    updatedRooms[columnIdx][positionIdx] = EMPTY
                    updatedRooms[destinyColumnIdx][firstEmpty] = char
                    val moveToFinalPosition =
                        movesToHallway + abs(destinyHallwayIdx - currentHallwayIdx) + (rooms[columnIdx].size - firstEmpty(
                            destinyColumnIdx
                        ))
                    return listOf(Pair(this.copy(rooms = updatedRooms), cost(char, moveToFinalPosition)))
                }
            }

            return emptyList()
        }

        private fun availableMovesFromColumnToHallway(columnIdx: Int, positionIdx: Int): List<Pair<Burrow, Long>> {
            val movesList = mutableListOf<Pair<Burrow, Long>>()
            val movesToHallway = rooms[columnIdx].size - positionIdx

            val char = rooms[columnIdx][positionIdx]
            val destinyColumnIdx = char - 'A'
            val currentHallwayIdx = 2 + 2 * columnIdx

            val updatedRooms = rooms.map { it.toMutableList() }
            updatedRooms[columnIdx][positionIdx] = EMPTY

            for (hallwayIdx in currentHallwayIdx downTo 0) {
                if (hallwayIdx in listOf(2, 4, 6, 8)) {
                    continue
                }
                val moveToFinalPosition = movesToHallway + currentHallwayIdx - hallwayIdx
                if (hallway[hallwayIdx] != EMPTY) {
                    break
                }
                val updatedHallway = hallway.toMutableList()
                updatedHallway[hallwayIdx] = char
                movesList.add(
                    Pair(
                        this.copy(rooms = updatedRooms, hallway = updatedHallway),
                        cost(char, moveToFinalPosition)
                    )
                )
            }
            for (hallwayIdx in currentHallwayIdx until hallway.size) {
                if (hallwayIdx in listOf(2, 4, 6, 8)) {
                    continue
                }
                val moveToFinalPosition = movesToHallway + hallwayIdx - currentHallwayIdx
                if (hallway[hallwayIdx] != EMPTY) {
                    break
                }
                val updatedHallway = hallway.toMutableList()
                updatedHallway[hallwayIdx] = char
                movesList.add(
                    Pair(
                        this.copy(rooms = updatedRooms, hallway = updatedHallway),
                        cost(char, moveToFinalPosition)
                    )
                )
            }

            return movesList
        }

        private fun availableMovesFromHallwayToColumn(currentHallwayIdx: Int): List<Pair<Burrow, Long>> {
            val char = hallway[currentHallwayIdx]
            val destinyColumnIdx = char - 'A'
            val destinyHallwayIdx = 2 + 2 * destinyColumnIdx
            val range = when (currentHallwayIdx > destinyHallwayIdx) {
                true -> currentHallwayIdx - 1 downTo destinyHallwayIdx
                false -> currentHallwayIdx + 1..destinyHallwayIdx
            }
            val canMoveToDestinyColumn = range.all { hallway[it] == EMPTY }
            if (canMoveToDestinyColumn && canPutInto(destinyColumnIdx)) {
                val firstEmpty = firstEmpty(destinyColumnIdx)
                val updatedRooms = rooms.map { it.toMutableList() }
                updatedRooms[destinyColumnIdx][firstEmpty] = char
                val updatedHallway = hallway.toMutableList()
                updatedHallway[currentHallwayIdx] = EMPTY
                val moveToFinalPosition =
                    abs(destinyHallwayIdx - currentHallwayIdx) + (rooms.first().size - firstEmpty(destinyColumnIdx))
                return listOf(
                    Pair(
                        this.copy(rooms = updatedRooms, hallway = updatedHallway),
                        cost(char, moveToFinalPosition)
                    )
                )
            }
            return emptyList()
        }

        private fun cost(char: Char, moves: Int): Long {
            return moves * when (char) {
                'A' -> 1L
                'B' -> 10
                'C' -> 100
                'D' -> 1000
                else -> throw Exception("Invalid char $char")
            }
        }

        private fun movableColumn(columnIdx: Int): Int? {
            val lastFilled = rooms[columnIdx].indexOfLast { it != EMPTY }
            if (lastFilled < 0) {
                return null
            }
            if (rooms[columnIdx][lastFilled] != roomChar(columnIdx)) {
                return lastFilled
            }
            if (lastFilled >= finalElements(columnIdx)) {
                return lastFilled
            }

            return null
        }

        fun complete(): Boolean {
            return (0..3).all { complete(it) }
        }

        private fun complete(columnIdx: Int): Boolean {
            return rooms[columnIdx].all { it == roomChar(columnIdx) }
        }

        private fun canPutInto(columnIdx: Int): Boolean {
            val empty = rooms[columnIdx].count { it == EMPTY }
            return rooms[columnIdx].size == finalElements(columnIdx) + empty
        }

        private fun firstEmpty(columnIdx: Int): Int {
            return rooms[columnIdx].indexOfFirst { it == EMPTY }
        }

        private fun finalElements(columnIdx: Int): Int {
            var filled = 0
            for (i in rooms[columnIdx].indices) {
                if (rooms[columnIdx][i] == roomChar(columnIdx)) {
                    filled++
                } else {
                    break
                }
            }
            return filled
        }

        private fun roomChar(columnIdx: Int): Char {
            return 'A' + columnIdx
        }
    }
}