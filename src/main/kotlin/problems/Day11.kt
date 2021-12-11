package problems

class Day11(override val input: String) : Problem {
    override val number: Int = 11
    private val board = Board.fromStr(input)

    override fun runPartOne(): String {
        val currentBoard = board.copy()
        var flashes = 0
        (1 .. 100).forEach { step ->
            flashes += currentBoard.runStep()
        }
        return flashes.toString()
    }

    data class Board(val board: MutableList<MutableList<Int>>) {
        companion object {
            fun fromStr(input: String): Board {
                return Board(input.lines()
                    .map{ line ->
                        line
                            .toCharArray()
                            .map{ i -> i.digitToInt() }
                            .toMutableList()
                    }.toMutableList())
            }
        }

        private val width = board.first().size
        private val height = board.size

        fun runStep(): Int {
            val toVisit = mutableListOf<Pair<Int,Int>>()
            val visited = mutableSetOf<Pair<Int,Int>>()
            for (m in 0 until height) {
                for (n in 0 until width) {
                    board[m][n] += 1
                    if (board[m][n] > 9) {
                        toVisit.add(Pair(m,n))
                    }
                }
            }

            var flashes = 0
            while(toVisit.isNotEmpty()) {
                val pointCoords = toVisit.removeAt(0)
                if (visited.contains(pointCoords)) continue
                flashes++
                visited.add(pointCoords)

                pointCoords
                    .neighbours()
                    .forEach{ neighbour ->
                        val (m,n) = neighbour
                        board[m][n] += 1
                        if (board[m][n] > 9) {
                            toVisit.add(0, neighbour)
                        }
                }

                visited.forEach { (m,n) -> board[m][n] = 0 }

            }

            return flashes
        }

        private fun Pair<Int, Int>.neighbours(): List<Pair<Int, Int>> {
            return listOf(
                Pair(1, 0),
                Pair(1, 1),
                Pair(0, 1),
                Pair(-1, 1),
                Pair(-1, 0),
                Pair(-1, -1),
                Pair(0, -1),
                Pair(1, -1),
            ).map { (m, n) -> Pair(this.first + m, this.second + n) }
            .filter { (m, n) -> m in 0 until height && n in 0 until width }
        }
    }
}