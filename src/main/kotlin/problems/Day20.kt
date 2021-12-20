package problems

class Day20(override val input: String) : Problem {
    override val number: Int = 20
    private val algorithm = input.lines().first()
    private val LIT = '#'
    private val pixelToBinary = mapOf('.' to '0', LIT to '1')

    override fun runPartOne(): String {
        val inputImage = input
            .split("\n\n")[1]
            .lines()
            .map { it.toCharArray().toList() }

        val runs = 2
        val padding = runs + 1
        val newInputImage =
            List(inputImage.size + (2 * padding)) { MutableList(inputImage.first().size + (2 * padding)) { '.' } }
        for (m in inputImage.indices) {
            for (n in inputImage.first().indices) {
                newInputImage[m + padding][n + padding] = inputImage[m][n]
            }
        }

        return newInputImage
            .applyImageEnhancementAlgorithm()
            .applyImageEnhancementAlgorithm()
            .drop(1)
            .dropLast(1)
            .map { it.drop(1).dropLast(1) }
            .sumOf { line -> line.count { it == LIT } }
            .toString()
    }

    private fun List<List<Char>>.applyImageEnhancementAlgorithm(): List<List<Char>> {
        val newList = List(this.size) { MutableList(this.first().size) { '.' } }

        for (m in newList.indices) {
            for (n in newList.first().indices) {
                val algorithmIdx = Pair(m, n)
                    .neighbours()
                    .map { (nm, nn) ->
                        val inBounds = (nm in (0 until this.size)) && (nn in (0 until this.first().size))
                        when (inBounds) {
                            false -> '.'
                            else -> this[nm][nn]
                        }
                    }
                    .map { pixelToBinary[it]!! }
                    .joinToString(separator = "")
                    .toInt(2)

                newList[m][n] = algorithm[algorithmIdx]
            }
        }
        return newList
    }

    private fun Pair<Int, Int>.neighbours(): List<Pair<Int, Int>> {
        return listOf(
            Pair(-1, -1),
            Pair(-1, 0),
            Pair(-1, 1),
            Pair(0, -1),
            Pair(0, 0),
            Pair(0, 1),
            Pair(1, -1),
            Pair(1, 0),
            Pair(1, 1),
        )
            .map { Pair(it.first + this.first, it.second + this.second) }
    }
}