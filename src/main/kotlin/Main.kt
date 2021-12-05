import problems.*
import java.net.URL

fun main() {
    val problems: List<Problem> = listOf(
        Day0("problems/day0".asResource().readText()),
        Day1("problems/day1".asResource().readText()),
        Day2("problems/day2".asResource().readText()),
        Day3("problems/day3".asResource().readText()),
        Day4("problems/day4".asResource().readText()),
        Day5("problems/day5".asResource().readText()),
//        Day6("problems/day6".asResource().readText()),
//        Day7("problems/day7".asResource().readText()),
//        Day8("problems/day8".asResource().readText()),
//        Day9("problems/day9".asResource().readText()),
//        Day10("problems/day10".asResource().readText()),
//        Day11("problems/day11".asResource().readText()),
//        Day12("problems/day12".asResource().readText()),
//        Day13("problems/day13".asResource().readText()),
//        Day14("problems/day14".asResource().readText()),
//        Day15("problems/day15".asResource().readText()),
//        Day16("problems/day16".asResource().readText()),
//        Day17("problems/day17".asResource().readText()),
//        Day18("problems/day18".asResource().readText()),
//        Day19("problems/day19".asResource().readText()),
//        Day20("problems/day20".asResource().readText()),
//        Day21("problems/day21".asResource().readText()),
//        Day22("problems/day22".asResource().readText()),
//        Day23("problems/day23".asResource().readText()),
//        Day24("problems/day24".asResource().readText()),
//        Day25("problems/day25".asResource().readText()),
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