package problems

class Day22(override val input: String) : Problem {
    override val number: Int = 22
    private val inputRegex = """(on|off) x=(-?\d*)..(-?\d*),y=(-?\d*)..(-?\d*),z=(-?\d*)..(-?\d*)""".toRegex()
    private val intervals = input.lines()
        .map { inputRegex.find(it)!!.groupValues.drop(1) }
        .map {
            Pair(
                it[0],
                listOf(it[1].toInt()..it[2].toInt(), it[3].toInt()..it[4].toInt(), it[5].toInt()..it[6].toInt())
            )
        }

    override fun runPartOne(): String {
        val intervalsInRange = intervals.filter { interval -> interval.second.all { axis -> axis in (-50..50) } }

        return onCubes(intervalsInRange)
            .sumOf { it[0].count() * it[1].count() * it[2].count() }
            .toString()
    }

    override fun runPartTwo(): String {
        return onCubes(intervals)
            .sumOf { 1L * it[0].count() * it[1].count() * it[2].count() }
            .toString()
    }


    private fun onCubes(inputs: List<Pair<String, List<IntRange>>>): List<List<IntRange>> {
        val currentIntervals = mutableListOf<List<IntRange>>()
        val toVisit = inputs.toMutableList()

        while (toVisit.isNotEmpty()) {
            val (type, interval) = toVisit.removeAt(0)
            val firstOverlap = currentIntervals.firstOrNull { currentInterval ->
                currentInterval[0] in interval[0]
                        && currentInterval[1] in interval[1]
                        && currentInterval[2] in interval[2]
            }

            if (firstOverlap != null) {
                val intervalSplitByOverlapPlanes = splitCubeByCubesPlanes(interval, firstOverlap)
                if (type == "on") {
                    val nonOverlappingCubes =
                        intervalSplitByOverlapPlanes.filter { !(it[0] in firstOverlap[0] && it[1] in firstOverlap[1] && it[2] in firstOverlap[2]) }
                    toVisit.addAll(0, nonOverlappingCubes.map { Pair(type, it) })
                } else {
                    val overlapSplitByIntervalPlanes = splitCubeByCubesPlanes(firstOverlap, interval)
                    val overlapIntervalMinusOff = overlapSplitByIntervalPlanes - intervalSplitByOverlapPlanes
                    currentIntervals.remove(firstOverlap)
                    currentIntervals.addAll(overlapIntervalMinusOff)
                    toVisit.add(0, Pair(type, interval))
                }
            } else {
                if (type == "on") {
                    currentIntervals += interval
                }
            }
        }
        return currentIntervals
    }

    private fun splitCubeByCubesPlanes(cube: List<IntRange>, planesCube: List<IntRange>): List<List<IntRange>> {
        return splitX(cube, planesCube[0].first - 1)
            .flatMap { splitX(it, planesCube[0].last) }
            .flatMap { splitY(it, planesCube[1].first - 1) }
            .flatMap { splitY(it, planesCube[1].last) }
            .flatMap { splitZ(it, planesCube[2].first - 1) }
            .flatMap { splitZ(it, planesCube[2].last) }
    }

    private fun splitX(cube: List<IntRange>, x: Int): List<List<IntRange>> {
        if (x !in cube[0]) {
            return listOf(cube)
        }

        val splitX = listOf(cube[0].first..x, (x + 1)..cube[0].last)
        return splitX
            .filter { range -> !range.isEmpty() }
            .map { range -> listOf(range, cube[1], cube[2]) }
    }

    private fun splitY(cube: List<IntRange>, y: Int): List<List<IntRange>> {
        if (y !in cube[1]) {
            return listOf(cube)
        }

        val splitY = listOf(cube[1].first..y, (y + 1)..cube[1].last)
        return splitY
            .filter { range -> !range.isEmpty() }
            .map { range -> listOf(cube[0], range, cube[2]) }
    }

    private fun splitZ(cube: List<IntRange>, z: Int): List<List<IntRange>> {
        if (z !in cube[2]) {
            return listOf(cube)
        }

        val splitZ = listOf(cube[2].first..z, (z + 1)..cube[2].last)
        return splitZ
            .filter { range -> !range.isEmpty() }
            .map { range -> listOf(cube[0], cube[1], range) }
    }

    operator fun IntRange.contains(other: IntRange): Boolean {
        return this.first <= other.last && other.first <= this.last
    }
}