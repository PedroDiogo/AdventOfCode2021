import problems.*
import java.net.URL

fun main() {
    val problems: List<Problem> = listOf(
        Day0("problems/day0".asResource().readText()),
        Day1("problems/day1".asResource().readText()),
        Day2("problems/day2".asResource().readText()),
        Day3("problems/day3".asResource().readText())
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