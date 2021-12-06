package problems

class Day6(override val input: String) : Problem {
    override val number: Int = 6
    private val fishAges = input.split(",").map { i -> i.toInt() }

    override fun runPartOne(): String {
        return runMultipleDays(80, fishAges).toString()
    }

    override fun runPartTwo(): String {
        return runMultipleDays(256, fishAges).toString()
    }

    private fun getNumberOfFishByAge(ageList: List<Int>): Array<Int> {
        return ageList
            .groupingBy { it }
            .eachCount()
            .entries
            .fold(Array(9) { 0 }) { list, (age, hits) ->
                list[age] = hits
                list
            }
    }

    private fun runMultipleDays(days: Int, ageList: List<Int>): Long {
        var numberOfFishByAge = getNumberOfFishByAge(ageList)
            .map { i -> i.toLong() }
            .toTypedArray()

        repeat(days) {
            numberOfFishByAge = runOneDay(numberOfFishByAge)
        }
        return numberOfFishByAge.sum()
    }

    private fun runOneDay(fishByAge: Array<Long>): Array<Long> {
        val first6Days = fishByAge.slice(1..6)
        val day6 = fishByAge[0] + fishByAge[7]
        val day7 = fishByAge[8]
        val day8 = fishByAge[0]

        return (first6Days + day6 + day7 + day8).toTypedArray()
    }
}