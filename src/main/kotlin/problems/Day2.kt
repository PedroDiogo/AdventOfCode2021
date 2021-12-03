package problems

class Day2(override val input: String) : Problem {
    override val number = 2
    private val inputList = input.lines().map { line ->
        val split = line.split(" ")
        Pair(split.first(), split.last().toInt())
    }

    override fun runPartOne(): String {
        val position = runCommands(SimplePosition())
        return (position.horizontal * position.depth).toString()
    }

    override fun runPartTwo(): String {
        val position = runCommands(PositionWithAim())
        return (position.horizontal * position.depth).toString()
    }

    private fun runCommands(initialPosition: Position): Position {
        return inputList.fold(initialPosition) { position, (action, increment) ->
            when (action) {
                "forward" -> position.goForward(increment)
                "up" -> position.goUp(increment)
                "down" -> position.goDown(increment)
                else -> position
            }
        }
    }

    private interface Position {
        val horizontal: Int
        val depth: Int

        fun goForward(increment: Int): Position
        fun goUp(increment: Int): Position
        fun goDown(increment: Int): Position
    }

    private class SimplePosition(override val horizontal: Int = 0, override val depth: Int = 0) : Position {
        override fun goForward(increment: Int): Position = SimplePosition(horizontal + increment, depth)
        override fun goUp(increment: Int): Position = SimplePosition(horizontal, depth - increment)
        override fun goDown(increment: Int): Position = goUp(-increment)
    }

    private class PositionWithAim(override val horizontal: Int = 0, override val depth: Int = 0, val aim: Int = 0) :
        Position {
        override fun goForward(increment: Int): Position {
            return PositionWithAim(horizontal + increment, depth + aim * increment, aim)
        }

        override fun goUp(increment: Int): Position {
            return PositionWithAim(horizontal, depth, aim - increment)
        }

        override fun goDown(increment: Int): Position {
            return goUp(-increment)
        }
    }
}