package problems

class Day2(override val input: String) : Problem {
    override val number = 2
    private val inputList = input.lines().map { line ->
        val split = line.split(" ")
        Pair(split.first(), split.last().toInt())
        }

    override fun runPartOne(): String {
        val position = inputList.fold(Position()) { position, pair ->
            when(pair.first) {
                "forward" -> position.goForward(pair.second)
                "up" -> position.goUp(pair.second)
                "down" -> position.goDown(pair.second)
                else -> position
            }
        }

        return (position.horizontal * position.depth).toString()
    }

    private class Position(var horizontal: Int = 0, var depth: Int = 0) {
        fun goForward(increment: Int) : Position = Position(horizontal + increment, depth)
        fun goUp(increment: Int) : Position = Position(horizontal, depth - increment)
        fun goDown(increment: Int) : Position = goUp(-increment)
    }
}