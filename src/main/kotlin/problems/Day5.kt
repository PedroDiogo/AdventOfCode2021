package problems

import kotlin.math.max
import kotlin.math.min

class Day5(override val input: String) : Problem {
    override val number: Int = 5
    private val lines = input.lines().map { line -> Line.fromStr(line) }

    override fun runPartOne(): String {
        val straightLines = lines.filter { line -> line.isVertical() || line.isHorizontal() }

        return straightLines.flatMap { line -> line.points().toList() }
            .groupingBy { it }
            .eachCount()
            .count { (_, count) -> count > 1 }
            .toString()
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
            val (start, end) = when {
                isVertical() -> Pair(min(y1, y2), max(y1, y2))
                else -> Pair(min(x1, x2), max(x1, x2))
            }

            return (start..end).map { point ->
                when {
                    isVertical() -> Pair(x1, point)
                    else -> Pair(point, y1)
                }
            }.toSet()
        }
    }
}