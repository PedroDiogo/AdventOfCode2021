package problems

class Day13(override val input: String) : Problem {
    override val number: Int = 13
    private val dots = input
        .split("\n\n")
        .first()
        .lines()
        .map { line -> line.split(",") }
        .map { (x, y) -> Pair(x.toInt(), y.toInt()) }
        .toSet()

    private val folds = input
        .split("\n\n")[1]
        .lines()
        .map { line -> line.replace("fold along ", "") }
        .map { line -> line.split("=") }
        .map { (type, position) -> Pair(type, position.toInt()) }

    override fun runPartOne(): String {
        return fold(dots, folds.first())
            .count()
            .toString()
    }

    private fun fold(points: Set<Pair<Int, Int>>, fold: Pair<String, Int>): Set<Pair<Int, Int>> {
        return when (fold.first) {
            "y" -> foldVertical(points, fold.second)
            else -> foldHorizontal(points, fold.second)
        }
    }

    private fun foldVertical(points: Set<Pair<Int, Int>>, foldPosition: Int): Set<Pair<Int, Int>> {
        val (down, up) = points.partition { (_, y) -> y <= foldPosition }

        val newUp = up
            .map { (x, y) -> Pair(x, 2 * foldPosition - y) }

        return down.toSet() + newUp.toSet()
    }

    private fun foldHorizontal(points: Set<Pair<Int, Int>>, foldPosition: Int): Set<Pair<Int, Int>> {
        val (left, right) = points.partition { (x, _) -> x <= foldPosition }

        val newRight = right
            .map { (x, y) -> Pair(2 * foldPosition - x, y) }

        return left.toSet() + newRight.toSet()
    }
}