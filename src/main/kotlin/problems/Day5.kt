package problems

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class Day5(override val input: String) : Problem {
    override val number: Int = 5
    private val lines = input.lines().map { line -> Line.fromStr(line) }

    override fun runPartOne(): String {
        val straightLines = lines.filter { line -> line.isVertical() || line.isHorizontal() }

        return numberOfOverlappingPoints(straightLines)
            .toString()
    }

    override fun runPartTwo(): String {
        return numberOfOverlappingPoints(lines).toString()
    }

    private fun numberOfOverlappingPoints(lines: List<Line>): Int {
        return lines.flatMap { line -> line.points().toList() }
            .groupingBy { it }
            .eachCount()
            .count { (_, count) -> count > 1 }
    }

    private data class Line(val x1: Int, val y1: Int, val x2: Int, val y2: Int) {
        companion object {
            fun fromStr(lineStr: String): Line {
                val (x1, y1, x2, y2) = lineStr
                    .replace(" -> ", ",")
                    .split(",")
                    .map { i -> i.toInt() }
                return Line(x1, y1, x2, y2)
            }
        }

        fun isVertical(): Boolean {
            return x1 == x2
        }

        fun isHorizontal(): Boolean {
            return y1 == y2
        }

        fun points(): Set<Pair<Int, Int>> {
            val length = max(abs(y2 - y1), abs(x2 - x1)) + 1

            val (xRange, yRange) = when {
                isVertical() -> Pair(List(length) { x1 }, getRange(y1, y2))
                isHorizontal() -> Pair(getRange(x1, x2), List(length) { y1 })
                else -> Pair(getRange(x1, x2), getRange(y1, y2))
            }

            return xRange.zip(yRange)
                .toSet()
        }

        fun getRange(from: Int, to: Int): List<Int> {
            val results = (min(from, to)..max(from, to)).toList()
            return when {
                from < to -> results
                else -> results.reversed()
            }
        }
    }
}