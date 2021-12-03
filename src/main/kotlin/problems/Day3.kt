package problems

class Day3(override val input: String) : Problem {
    override val number: Int = 3
    private val inputList = input.lines()
        .map { line -> line.toCharArray().map { c -> c.digitToInt() }.toList() }

    override fun runPartOne(): String {
        val sumList = Array(inputList.first().size) { _ -> 0 }

        inputList.forEach { line ->
            line.forEachIndexed { index, i ->
                sumList[index] += i
            }
        }

        val gammaRate = sumList
            .map { i ->
                when {
                    i >= inputList.size / 2 -> 1
                    else -> 0
                }
            }
            .joinToString(separator = "")
            .toInt(2)

        val epsilonRate = gammaRate.inv() and (1.shl(inputList.first().size) - 1)

        return (gammaRate * epsilonRate).toString()
    }
}