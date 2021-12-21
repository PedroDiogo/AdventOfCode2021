package problems

class Day21(override val input: String) : Problem {
    override val number: Int = 21
    private val startingPositions = input.lines().map { it.split(": ")[1].toInt() - 1 }

    override fun runPartOne(): String {
        var currentPlayer = 0
        val scores = mutableListOf(0, 0)
        val currentPositions = startingPositions.toMutableList()

        val dice = DeterministicDice()

        while (!scores.any { it >= 1000 }) {
            val roll = dice.rollThree()
            currentPositions[currentPlayer] = (currentPositions[currentPlayer] + roll) % 10
            scores[currentPlayer] += currentPositions[currentPlayer] + 1
            currentPlayer = (currentPlayer + 1) % 2
        }
        return (dice.rolls * scores.filter { it < 1000 }.single()).toString()
    }

    private class DeterministicDice {
        var rolls = 0
        private var currentNumber = 0

        fun rollThree(): Int {
            val roll = 3 * (currentNumber + 1) + 3
            currentNumber = (currentNumber + 3) % 100
            rolls += 3

            return roll
        }
    }
}