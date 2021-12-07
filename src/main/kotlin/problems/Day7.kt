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
        val distinctSortedCrabPositions = crabPositions.distinct().sorted()

        val result = distinctSortedCrabPositions.map { toPosition ->
            calculateComplexFuel(crabPositions, toPosition)
        }
        val minimumResult = result.minOf { fuel -> fuel }
        val minimumResultIndex = result.indexOf(minimumResult)

        val minimumNeighbours =
            distinctSortedCrabPositions[minimumResultIndex - 1]..distinctSortedCrabPositions[minimumResultIndex + 1]

        return minimumNeighbours
            .map { toPosition ->
                calculateComplexFuel(crabPositions, toPosition)
            }.minOf { fuel -> fuel }
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