package problems

class Day4(override val input: String) : Problem {
    override val number: Int = 4

    private val drawNumbers = input.lines().first().split(",").map { i -> i.toInt() }
    private val boards = input.split("\n\n").drop(1).map { boardStr -> Board(boardStr) }

    override fun runPartOne(): String {
        var newBoards = boards
        for (number in drawNumbers) {
            newBoards = newBoards.map { board -> board.play(number) }

            val bingoBoards = boards.filter { board -> board.isBingo() }
            if (bingoBoards.isNotEmpty()) {
                return (bingoBoards.single().unmarkedNumbers().sum() * number).toString()
            }
        }
        return "Not found"
    }

    private class Board(boardStr: String) {
        private val boardMatrix = convertBoardStrToMatrix(boardStr)
        private val rows = boardMatrix.size
        private val columns = boardMatrix.first().size

        private val numbersCoordinates = getNumbersCoordinates(boardMatrix)
        private val playedNumbers = mutableSetOf<Int>()

        private val numberMarkedNumbersRow = MutableList(rows) { 0 }
        private val numberMarkedNumbersCol = MutableList(columns) { 0 }

        private fun convertBoardStrToMatrix(boardStr: String): List<List<Int>> {
            return boardStr.lines().map { line ->
                line
                    .split(" ")
                    .filter { digit -> digit.isNotBlank() }
                    .map { i -> i.toInt() }
            }
        }

        private fun getNumbersCoordinates(boardMatrix: List<List<Int>>): Map<Int, Pair<Int, Int>> {
            return boardMatrix.flatMapIndexed { rowNumber, row ->
                row.mapIndexed { colNumber, number ->
                    number to Pair(rowNumber, colNumber)
                }
            }.toMap()
        }

        fun unmarkedNumbers(): Set<Int> {
            return numbersCoordinates.keys.minus(playedNumbers)
        }

        fun play(number: Int): Board {
            if (!numbersCoordinates.containsKey(number)) {
                return this
            }
            val (row, col) = numbersCoordinates[number]!!
            playedNumbers.add(number)
            numberMarkedNumbersRow[row] += 1
            numberMarkedNumbersCol[col] += 1
            return this
        }

        fun isBingo(): Boolean {
            return numberMarkedNumbersRow.any { markedNumbers -> markedNumbers == rows }
                .or(numberMarkedNumbersCol.any { markedNumbers -> markedNumbers == columns })
        }
    }
}