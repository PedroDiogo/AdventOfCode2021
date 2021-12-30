package problems

class Day25(override val input: String) : Problem {
    override val number: Int = 25
    private val initialBoard = input.lines().map { it.toCharArray() }
    private val width = input.lines().first().length
    private val height = input.lines().size

    private val EMPTY = '.'

    override fun runPartOne(): String {
        var currentBoard = initialBoard
        var steps = 0

        while (true) {
            steps++
            val newBoard = runStep(currentBoard)
            val didntChange = (0 until height).all { m ->
                (0 until width).all { n -> newBoard[m][n] == currentBoard[m][n] }
            }
            if (didntChange) {
                break
            }
            currentBoard = newBoard
        }
        return steps.toString()
    }

    fun runStep(board: List<CharArray>): List<CharArray> {
        val newBoard = List(height) { CharArray(width) { EMPTY } }

        for (m in board.indices) {
            for (n in board[m].indices) {
                val nextIdx = (n + 1) % width
                if (board[m][n] == '>') {
                    val nextIsEmpty = board[m][nextIdx] == EMPTY
                    if (nextIsEmpty) {
                        newBoard[m][nextIdx] = '>'
                    } else {
                        newBoard[m][n] = '>'
                    }
                }
            }
        }

        for (m in board.indices) {
            for (n in board[m].indices) {
                val nextIdx = (m + 1) % height
                if (board[m][n] == 'v') {
                    val nextFilledByPreviousStep = newBoard[nextIdx][n] != EMPTY
                    val nextIsEmpty = !nextFilledByPreviousStep && board[nextIdx][n] != 'v'

                    if (nextIsEmpty) {
                        newBoard[nextIdx][n] = 'v'
                    } else {
                        newBoard[m][n] = 'v'
                    }
                }
            }
        }
        return newBoard
    }
}