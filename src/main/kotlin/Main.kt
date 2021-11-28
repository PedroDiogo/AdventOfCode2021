import problems.Problem
import java.net.URL

fun main() {
    val problems: List<Problem> = listOf(
    )

    runProblems(problems)
}

fun runProblems(problems: List<Problem>) {
    for (day in problems) {
        println("Day ${day.number}")
        println("Part one:\n" +
                day.runPartOne())

        val partTwo = day.runPartTwo()
        if (partTwo.isNotEmpty())
            println("Part two:\n" +
                    partTwo)
        println("===============================")
    }
}

fun String.asResource() : URL {
    return object {}.javaClass.getResource(this)
}