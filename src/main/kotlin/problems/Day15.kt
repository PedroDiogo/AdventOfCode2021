package problems

import java.util.*

class Day15(override val input: String) : Problem {
    override val number: Int = 15

    override fun runPartOne(): String {
        val board = Board.fromStr(input)
        return board.lowestTotalRisk().toString()
    }

    override fun runPartTwo(): String {
        val board = Board.fromStr(input).expand(5)
        return board.lowestTotalRisk().toString()
    }

    data class Board(val board: MutableList<MutableList<Int>>) {
        companion object {
            fun fromStr(input: String): Board {
                return Board(
                    input.lines()
                        .map { line ->
                            line
                                .toCharArray()
                                .map { i -> i.digitToInt() }
                                .toMutableList()
                        }.toMutableList()
                )
            }
        }

        private val width = board.first().size
        private val height = board.size

        fun lowestTotalRisk(): Long {
            val distance = List(height) { MutableList(width) { Long.MAX_VALUE } }
            val queue = PriorityQueue(compareBy<Cell> { it.distance }
                .thenBy { it.m }
                .thenBy { it.n })

            distance[0][0] = 0
            queue.add(Cell(0, 0, 0))

            while (queue.isNotEmpty()) {
                val cell = queue.poll()!!
                cell.neighbours(height - 1, width - 1).forEach { n ->
                    val neighbourDistanceToCell = distance[cell.m][cell.n] + board[n.m][n.n]
                    if (distance[n.m][n.n] > neighbourDistanceToCell) {
                        queue.removeIf { it.m == n.m && it.n == n.n }
                        distance[n.m][n.n] = neighbourDistanceToCell
                        queue.add(Cell(n.m, n.n, neighbourDistanceToCell))
                    }
                }
            }
            return distance.last().last()
        }

        fun expand(by: Int): Board {
            val newWidth = width * by
            val newHeight = height * by
            val newBoard = MutableList(newHeight) { MutableList(newWidth) { 0 } }

            for (m in 0 until newHeight) {
                for (n in 0 until newHeight) {
                    val firstRow = m in 0 until height
                    val firstColumn = n in 0 until width

                    newBoard[m][n] = (
                            if (firstRow && firstColumn) {
                                board[m][n]
                            } else if (firstColumn) {
                                val upM = m - height
                                newBoard[upM][n] + 1
                            } else {
                                val leftN = n - width
                                newBoard[m][leftN] + 1
                            }
                            ).wrapValue()
                }
            }
            return Board(newBoard)
        }

        private fun Int.wrapValue(): Int {
            return when {
                this > 9 -> 1
                else -> this
            }
        }

        private data class Cell(val m: Int, val n: Int, val distance: Long) {
            fun neighbours(mMax: Int, nMax: Int): Set<Cell> {
                return setOf(
                    Pair(1, 0),
                    Pair(0, 1),
                    Pair(-1, 0),
                    Pair(0, -1),
                )
                    .map { (dM, dN) -> Cell(this.m + dM, this.n + dN, -1) }
                    .filter { cell -> cell.m in 0..mMax && cell.n in 0..nMax }
                    .toSet()
            }
        }
    }
}