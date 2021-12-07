package problems

import kotlin.math.abs

class Day7(override val input: String) : Problem {
    override val number: Int = 7
    private val crabPositions = input.split(",").map{ i-> i.toInt()}

    override fun runPartOne(): String {
        val median = crabPositions.median()
        return crabPositions.sumOf { position -> abs(position - median) }
            .toString()
    }

    private fun List<Int>.median() : Int {
        val sorted = this.sorted()
        return if (this.size % 2 == 1) {
            sorted[this.size/2];
        } else {
            (sorted[this.size/2-1] + sorted[this.size/2])/2
        }
    }
}