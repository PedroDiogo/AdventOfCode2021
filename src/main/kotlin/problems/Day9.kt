package problems

class Day9(override val input: String) : Problem {
    override val number: Int = 9
    private val board: Board = Board.fromStr(input)

    override fun runPartOne(): String {
        return board
            .localMinima()
            .sumOf { point -> point.riskLevel }
            .toString()
    }

    data class Board(val board: List<List<Point>>) {
        companion object {
            fun fromStr(input: String): Board {
                return Board(input.lines()
                    .mapIndexed { row, line ->
                        line
                            .toCharArray()
                            .mapIndexed { col, i -> Point(row, col, i.digitToInt()) }
                    })
            }
        }

        private val width = board.first().size
        private val height = board.size

        fun localMinima(): List<Point> {
            return board.flatten().filter { point -> neighbours(point).all { neighbour -> neighbour > point } }
        }

        private fun neighbours(point: Point): List<Point> {
            return listOf(
                Pair(point.m + 1, point.n),
                Pair(point.m - 1, point.n),
                Pair(point.m, point.n + 1),
                Pair(point.m, point.n - 1)
            )
                .filter { (m, n) -> m in 0 until height && n in 0 until width }
                .map { (m, n) -> board[m][n] }
        }

        data class Point(val m: Int, val n: Int, val height: Int) : Comparable<Point> {
            val riskLevel = height + 1
            override fun compareTo(other: Point): Int {
                return this.height.compareTo(other.height)
            }
        }
    }
}