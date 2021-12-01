package problems

class Day1(override val input: String) : Problem {
    override val number = 1
    private val inputList = input.lines().map { i -> i.toInt() }

    override fun runPartOne(): String {
           return getNumberOfIncreases(inputList)
               .toString()
    }

    private fun getNumberOfIncreases(list: List<Int>) : Int {
        return list
            .zipWithNext()
            .map { pair ->  pair.second > pair.first }
            .count { value -> value }
    }

    override fun runPartTwo(): String {
        val windowedSum = inputList.windowed(3)
            .map { sublist -> sublist.sum() }
        return getNumberOfIncreases(windowedSum)
            .toString()
    }
}