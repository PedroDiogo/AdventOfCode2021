package problems

interface Problem {
    val number: Int
    val input: String

    fun runPartOne(): String
    fun runPartTwo(): String = ""
}