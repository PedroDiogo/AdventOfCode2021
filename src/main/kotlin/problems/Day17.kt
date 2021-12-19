package problems

import kotlin.math.min

class Day17(override val input: String) : Problem {
    override val number: Int = 17
    private val inputMatch = """target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)"""
        .toRegex()
        .find(input)
        ?.groupValues
        ?.drop(1)
        ?.map { it.toInt() }!!
    private val xRange = inputMatch[0]..inputMatch[1]
    private val yRange = inputMatch[2]..inputMatch[3]

    override fun runPartOne(): String {
        val positions = mutableSetOf<Pair<Int,Int>>()
        for (vx in 0..xRange.last) {
            for (vy in yRange.first..500) {
                val position = positions(Pair(vx, vy))
                if (position != null) {
                    positions.addAll(position)
                }
            }
        }
        return positions
            .maxOf { it.second }
            .toString()
    }

    override fun runPartTwo(): String {
            var count = 0
            for (vx in 0..xRange.last) {
                for (vy in yRange.first..500) {
                    val position = positions(Pair(vx, vy))
                    if (position != null) {
                        count++
                    }
                }
            }
            return count
                .toString()
    }

    private fun positions(velocity: Pair<Int, Int>) : Set<Pair<Int, Int>>? {
        val positions = mutableSetOf<Pair<Int, Int>>()
        var (x, y) = Pair(0,0)
        var (vx,vy) = velocity

        while(true) {
            x += vx
            y += vy
            positions.add(Pair(x,y))

            vx = when {
                vx < 0 -> vx + 1
                vx > 0 -> vx - 1
                else -> vx
            }
            vy -= 1
            val withinBounds = x in xRange && y in yRange
            val outOfBounds = x > xRange.last || y < min(yRange.first, yRange.last)
            if (withinBounds) {
                return positions
            }
            if (outOfBounds) {
                return null
            }
        }
    }
}