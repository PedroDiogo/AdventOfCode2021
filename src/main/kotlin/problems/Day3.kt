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
        val epsilonRate = gammaRate.xor(mask)

        return (gammaRate * epsilonRate).toString()
    }

    override fun runPartTwo(): String {
        val mostCommonBitBitCriteria = { l: List<List<Int>>, idx: Int ->
            mostCommonBitPerPosition(l)[idx].digitToInt()
        }
        val leastCommonBitBitCriteria = { l: List<List<Int>>, idx: Int ->
            mostCommonBitBitCriteria(l, idx).xor(1)
        }

        val oxygenGeneratorRating = searchRating(inputList, mostCommonBitBitCriteria)
        val co2ScrubberRating = searchRating(inputList, leastCommonBitBitCriteria)

        return (oxygenGeneratorRating * co2ScrubberRating).toString()
    }

    private fun mostCommonBitPerPosition(list: List<List<Int>>): String {
        val numberOfNumbers = list.size

        return rotateRight90(list)
            .map { row -> row.sum() }
            .map { i ->
                when {
                    2 * i >= numberOfNumbers -> 1
                    else -> 0
                }
            }
            .joinToString(separator = "")
    }

    private fun rotateRight90(list: List<List<Int>>): List<List<Int>> {
        val rows = list.size
        val columns = list.first().size
        val rotatedList = List(columns) { MutableList(rows) { 0 } }

        for (m in 0 until rows) {
            for (n in 0 until columns) {
                rotatedList[n][m] = list[m][n]
            }
        }
        return rotatedList
    }

    private fun searchRating(initial: List<List<Int>>, bitCriteria: (list: List<List<Int>>, index: Int) -> Int): Int {
        var list = initial
        var index = 0
        while (list.size > 1) {
            list = list.filter { number ->
                number[index] == bitCriteria(list, index)
            }
            index++
        }
        return list.single().joinToString(separator = "").toInt(2)
    }
}