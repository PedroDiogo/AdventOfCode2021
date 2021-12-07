package problems

import kotlin.math.abs

class Day7(override val input: String) : Problem {
    override val number: Int = 7
    private val crabPositions = input.split(",").map { i -> i.toInt() }

    override fun runPartOne(): String {
        val median = crabPositions.median()
        return crabPositions.sumOf { position -> abs(position - median) }
            .toString()
    }

    private fun List<Int>.median(): Int {
        val sorted = this.sorted()
        return if (this.size % 2 == 1) {
            sorted[this.size / 2]
        } else {
            (sorted[this.size / 2 - 1] + sorted[this.size / 2]) / 2
        }
    }

    override fun runPartTwo(): String {
        val average = crabPositions.average()
        return ((average-1).toInt()..(average+1).toInt())
            .map { position -> calculateComplexFuel(crabPositions, position) }.minOf { it }
            .toString()
    }

    private fun calculateComplexFuel(positions: List<Int>, toPosition: Int): Int {
        return positions.sumOf { position ->
            val distance = abs(position - toPosition)
            // Partial sum - 1+2+3+...+distance
            distance * (distance + 1) / 2
        }
    }
}