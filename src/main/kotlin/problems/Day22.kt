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
        val cube = List(101) { List(101) { MutableList(101) { false } } }
        val intervalsInRange = intervals.filter { interval -> interval.second.all { axis -> axis in (-50..50) } }

        for (interval in intervalsInRange) {
            for (x in interval.second[0]) {
                for (y in interval.second[1]) {
                    for (z in interval.second[2]) {
                        cube[x+50][y+50][z+50] = when(interval.first) {
                            "on" -> true
                            else -> false
                        }
                    }
                }
            }
        }

        return cube.sumOf { x ->
            x.sumOf { y ->
                y.count { it }
            }
        }.toString()
    }

    operator fun IntRange.contains(other: IntRange) : Boolean {
        return this.first <= other.last && other.first <= this.last
    }
}

// On -> Intersects? No. -> Add as new range ; Yes -> Add intersection
// Off -> Intersects? No -> Do nothing; Yes -> Split all intersections