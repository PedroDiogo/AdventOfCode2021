package problems

class Day3(override val input: String) : Problem {
    override val number: Int = 3
    private val inputList = input.lines()
        .map { line -> line.toCharArray().map { c -> c.digitToInt() }.toList() }

    override fun runPartOne(): String {
        val gammaRate = mostCommonBitPerPosition(inputList)
            .toInt(2)

        val numberOfBits = inputList.first().size
        val mask = 1.shl(numberOfBits) - 1
        val epsilonRate = gammaRate.inv() and (mask)

        return (gammaRate * epsilonRate).toString()
    }

    override fun runPartTwo(): String {
        var list = inputList
        var index = 0
        while (list.size > 1) {
            val mostCommonBit = mostCommonBitPerPosition(list)[index].digitToInt()
            list = list.filter { number ->
                number[index] == mostCommonBit
            }
            index++
        }

        val oxigenGeneratorRating = list.single().joinToString(separator = "").toInt(2)

        list = inputList
        index = 0
        while (list.size > 1) {
            val mostCommonBit = mostCommonBitPerPosition(list)[index].digitToInt()
            val leastCommonBit = when (mostCommonBit) {
                0 -> 1
                else -> 0
            }

            list = list.filter { number ->
                number[index] == leastCommonBit
            }
            index++
        }

        val co2ScrubberRating = list.single().joinToString(separator = "").toInt(2)
        return (oxigenGeneratorRating * co2ScrubberRating).toString()
    }

    private fun mostCommonBitPerPosition(list: List<List<Int>>): String {
        val numberOfBits = list.first().size
        val numberOfNumbers = list.size
        val sumList = Array(numberOfBits) { _ -> 0 }

        list.forEach { line ->
            line.forEachIndexed { index, i ->
                sumList[index] += i
            }
        }

        return sumList
            .map { i ->
                when {
                    2 * i >= numberOfNumbers -> 1
                    else -> 0
                }
            }
            .joinToString(separator = "")
    }
}