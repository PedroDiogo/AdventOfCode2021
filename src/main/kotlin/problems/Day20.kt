package problems

class Day20(override val input: String) : Problem {
    override val number: Int = 20
    private val algorithm = input.lines().first()
    private val LIT = '#'
    private val pixelToBinary = mapOf('.' to '0', LIT to '1')

    override fun runPartOne(): String {
        return runAlgorithm(2)
    }

    override fun runPartTwo(): String {
        return runAlgorithm(50)
    }

    private fun runAlgorithm(runs: Int): String {
        val inputImage = input
            .split("\n\n")[1]
            .lines()
            .map { it.toCharArray().toList() }

        return (1..runs).fold(inputImage) { acc, run ->
            val empty = when {
                algorithm[0] == '.' -> '.'
                run % 2 == 1 -> '.'
                else -> '#'
            }
            acc.applyImageEnhancementAlgorithm(empty)
        }
            .sumOf { line -> line.count { it == LIT } }
            .toString()
    }

    private fun List<List<Char>>.applyImageEnhancementAlgorithm(empty: Char = '.'): List<MutableList<Char>> {
        val newList = List(this.size + 2) { MutableList(this.first().size + 2) { empty } }

        for (m in newList.indices) {
            for (n in newList.first().indices) {
                val algorithmIdx = Pair(m, n)
                    .neighbours()
                    .map { (nm, nn) ->
                        val inBounds = ((nm - 1) in (0 until this.size)) && ((nn - 1) in (0 until this.first().size))
                        when (inBounds) {
                            false -> empty
                            else -> this[nm - 1][nn - 1]
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