package problems

import java.util.*

class Day15(override val input: String) : Problem {
    override val number: Int = 15

    override fun runPartOne(): String {
        val board = Board.fromStr(input)
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
                .thenBy{ it.m }
                .thenBy{ it.n })

            distance[0][0] = 0
            queue.add(Cell(0, 0, 0))

            while (queue.isNotEmpty()) {
                val cell = queue.poll()!!
                cell.neighbours(height - 1, width - 1).forEach { n ->
                    val neighbourDistanceToCell = distance[cell.m][cell.n] + board[n.m][n.n]
                    if (distance[n.m][n.n] > neighbourDistanceToCell) {
                        queue.removeIf{it.m == n.m && it.n == n.n}
                        distance[n.m][n.n] = neighbourDistanceToCell
                        queue.add(Cell(n.m, n.n, neighbourDistanceToCell))
                    }
                }
            }
            return distance.last().last()
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