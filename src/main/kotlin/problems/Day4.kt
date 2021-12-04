package problems

class Day4(override val input: String) : Problem {
    override val number: Int = 4

    private val drawNumbers = input.lines().first().split(",").map { i -> i.toInt() }
    private val boards = input.split("\n\n").drop(1).map { boardStr -> Board.fromBoardString(boardStr) }

    override fun runPartOne(): String {
        var newBoards = boards
        for (number in drawNumbers) {
            newBoards = newBoards.map { board -> board.play(number) }

            val bingoBoards = newBoards.filter { board -> board.isBingo() }
            if (bingoBoards.isNotEmpty()) {
                return (bingoBoards.single().unmarkedNumbers().sum() * number).toString()
            }
        }
        return "Not found"
    }

    override fun runPartTwo(): String {
        var newBoards = boards
        var previousRoundBoards = boards

        for (number in drawNumbers) {
            newBoards = newBoards
                .map { board -> board.play(number) }
                .filter { board -> !board.isBingo() }

            if (newBoards.isEmpty()) {
                return ((previousRoundBoards.single().unmarkedNumbers().sum()-number) * number).toString()
            }
            previousRoundBoards = newBoards
        }
        return "Not found"
    }

    private data class Board(val boardMatrix: List<List<Int>>, val playedNumbers: Set<Int>) {
        private val rows = boardMatrix.size
        private val columns = boardMatrix.first().size
        private val numbersCoordinates = getNumbersCoordinates(boardMatrix)

        companion object {
            fun fromBoardString(boardStr: String): Board {
                val boardMatrix = boardStr.lines().map { line ->
                    line
                        .split(" ")
                        .filter { digit -> digit.isNotBlank() }
                        .map { i -> i.toInt() }
                }
                return Board(boardMatrix, setOf())
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
            val newBoard = this.copy(playedNumbers = playedNumbers + number)
            return newBoard
        }

        fun isBingo(): Boolean {
            val playedCoordinates = playedNumbers.map { playedNumber -> numbersCoordinates[playedNumber]!! }
            val bingoOnRows = playedCoordinates
                .groupingBy { (row, _) -> row }
                .eachCount()
                .any { (_, numberOfMarkedNumbers) -> numberOfMarkedNumbers == rows }
            val bingoOnColumns = playedNumbers.map { numbersCoordinates[it]!! }
                .groupingBy { (_, col) -> col }
                .eachCount()
                .any { (_, numberOfMarkedNumbers) -> numberOfMarkedNumbers == columns }

            return bingoOnRows.or(bingoOnColumns)
        }
    }
}