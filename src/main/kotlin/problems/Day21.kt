package problems

import kotlin.math.max

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

    override fun runPartTwo(): String {
        val toVisit = mutableSetOf<GameState>()
        val toVisitHash = mutableMapOf<GameState, Long>()
        var player1Won = 0L
        var player2Won = 0L
        val targetScore = 21
        val startingPosition = GameState(0, startingPositions[0].toByte(), 0, startingPositions[1].toByte(), 1)
        toVisit.add(startingPosition)
        toVisitHash[startingPosition] = 1


        while (toVisit.isNotEmpty()) {
            val currentGame = toVisit.first()
            toVisit.remove(currentGame)

            val next = playerRound(currentGame)
            val p1Won = next.count { it.player1Score >= targetScore }
            val p2Won = next.count { it.player2Score >= targetScore }
            val toVisitNext = next.filter { it.player1Score < targetScore && it.player2Score < targetScore }

            val count = toVisitHash[currentGame] ?: 0
            toVisitHash[currentGame] = 0

            player1Won += count * p1Won
            player2Won += count * p2Won
            toVisitNext.forEach {
                toVisitHash[it] = (toVisitHash[it] ?: 0) + count
            }
            toVisit.addAll(toVisitNext)
        }
        return max(player1Won, player2Won).toString()
    }

    private fun playerRound(starting: GameState, round: Int = 1): List<GameState> {
        return if (round == 3) {
            listOf(
                starting.movePlayer(starting.nextPlayer, 1, true),
                starting.movePlayer(starting.nextPlayer, 2, true),
                starting.movePlayer(starting.nextPlayer, 3, true)
            )
        } else {
            playerRound(starting.movePlayer(starting.nextPlayer, 1, false), round + 1) +
                    playerRound(starting.movePlayer(starting.nextPlayer, 2, false), round + 1) +
                    playerRound(starting.movePlayer(starting.nextPlayer, 3, false), round + 1)
        }
    }

    private data class GameState(
        val player1Score: Byte = 0,
        val player1Position: Byte,
        val player2Score: Byte = 0,
        val player2Position: Byte,
        val nextPlayer: Byte = 1
    ) {
        fun movePlayer(playerNo: Byte, moves: Byte, finalRoll: Boolean): GameState {
            return when (playerNo) {
                1.toByte() -> {
                    val newPosition = (player1Position + moves) % 10
                    if (finalRoll) {
                        val newScore = player1Score + newPosition + 1
                        this.copy(
                            player1Position = newPosition.toByte(),
                            player1Score = newScore.toByte(),
                            nextPlayer = 2
                        )
                    } else {
                        this.copy(player1Position = newPosition.toByte())
                    }
                }
                else -> {
                    val newPosition = (player2Position + moves) % 10
                    if (finalRoll) {
                        val newScore = player2Score + newPosition + 1
                        this.copy(
                            player2Position = newPosition.toByte(),
                            player2Score = newScore.toByte(),
                            nextPlayer = 1
                        )
                    } else {
                        this.copy(player2Position = newPosition.toByte())
                    }
                }
            }
        }
    }
}