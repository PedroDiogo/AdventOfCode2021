package problems

import kotlin.math.ceil
import kotlin.math.floor

class Day18(override val input: String) : Problem {
    override val number: Int = 18
    private val numbers = input.lines().map { SnailfishNumber.fromString(it) }

    override fun runPartOne(): String {
        return numbers
            .reduce { acc, snailfishNumber -> (acc + snailfishNumber).reduce() }
            .magnitude()
            .toString()
    }

    override fun runPartTwo(): String {
        var maxMagnitude = 0L
        for (n1 in (numbers.indices)) {
            for (n2 in (n1 until numbers.size)) {
                maxMagnitude = listOf(
                    (numbers[n1] + numbers[n2]).reduce().magnitude(),
                    (numbers[n2] + numbers[n1]).reduce().magnitude(),
                    maxMagnitude
                ).maxOf { it }
            }
        }
        return maxMagnitude.toString()
    }

    data class SnailfishNumber(val left: SnailfishElement, val right: SnailfishElement) {
        companion object {
            fun fromString(str: String): SnailfishNumber {
                val strWithoutBrackets = str.substring((1 until str.length - 1))

                var openBrackets = 0
                val list = mutableListOf<String>()
                strWithoutBrackets
                    .toCharArray()
                    .forEachIndexed fei@{ idx, c ->
                        if (c == ',' && openBrackets == 0) {
                            list.add(strWithoutBrackets.substring(0 until idx))
                            list.add(strWithoutBrackets.substring(idx + 1))
                            return@fei
                        } else {
                            when (c) {
                                '[' -> openBrackets++
                                ']' -> openBrackets--
                                else -> {}
                            }
                        }
                    }
                val (left, right) = list

                return SnailfishNumber(SnailfishElement.fromString(left), SnailfishElement.fromString(right))
            }
        }

        override fun toString(): String {
            return "[${left},${right}]"
        }

        fun reduce(): SnailfishNumber {
            var previousNumber = SnailfishNumber(SnailfishElement(), SnailfishElement())
            var currentNumber = this
            while (previousNumber != currentNumber) {
                previousNumber = currentNumber
                currentNumber = currentNumber.explode()
                if (currentNumber != previousNumber) {
                    continue
                }
                currentNumber = currentNumber.split()
            }
            return currentNumber
        }

        private fun findFirstExplodedPair(): Pair<Int, Int>? {
            var level = 0
            var start: Int? = null
            this.toString().toCharArray().forEachIndexed { idx, char ->
                when (char) {
                    '[' -> level++
                    ']' -> level--
                }
                if (level == 5 && start == null) {
                    start = idx
                }
                if (level == 4 && start != null) {
                    return Pair(start!!, idx)
                }
            }
            return null
        }

        fun explode(): SnailfishNumber {
            val explodedPairIdxs = findFirstExplodedPair() ?: return this

            val (leftIdx, rightIdx) = explodedPairIdxs
            val thisStr = this.toString()
            val (leftRemainder, rightRemainder) = thisStr.substring(leftIdx + 1, rightIdx).split(",")
                .map { it.toLong() }
            val leftString = thisStr.substring(0, leftIdx)
            val rightString = thisStr.substring(rightIdx + 1)

            val digitRegex = """\d+""".toRegex()
            var findPairLeft = digitRegex.find(leftString)
            val newLeftString = if (findPairLeft != null) {
                var value = findPairLeft.value
                while (findPairLeft != null) {
                    value = findPairLeft.value
                    findPairLeft = findPairLeft.next()
                }
                val newValue = (value.toLong() + leftRemainder).toString()
                leftString.reversed().replaceFirst(value.reversed(), newValue.reversed()).reversed()
            } else {
                leftString
            }

            val findPairRight = digitRegex.find(rightString)
            val newRightString = if (findPairRight != null) {
                val newValue = (findPairRight.value.toLong() + rightRemainder).toString()
                rightString.replaceFirst(findPairRight.value, newValue)
            } else {
                rightString
            }

            return fromString(newLeftString + "0" + newRightString)
        }

        fun split(): SnailfishNumber {
            val newLeft = left.split()
            if (newLeft != left) {
                return SnailfishNumber(newLeft, right)
            }
            return SnailfishNumber(left, right.split())
        }

        operator fun plus(other: SnailfishNumber): SnailfishNumber {
            return SnailfishNumber(SnailfishElement(element = this.copy()), SnailfishElement(element = other.copy()))
        }

        fun magnitude(): Long {
            val leftMagnitude = 3 * (left.value ?: left.element!!.magnitude())
            val rightMagnitude = 2 * (right.value ?: right.element!!.magnitude())
            return leftMagnitude + rightMagnitude
        }
    }

    data class SnailfishElement(val value: Long? = null, val element: SnailfishNumber? = null) {
        companion object {
            fun fromString(str: String): SnailfishElement {
                return if (str[0].isDigit()) {
                    SnailfishElement(str.toLong())
                } else {
                    SnailfishElement(element = SnailfishNumber.fromString(str))
                }
            }
        }

        fun split(): SnailfishElement {
            return if (value != null) {
                if (value > 9) {
                    val halfValue = value.toFloat() / 2

                    SnailfishElement(
                        element = SnailfishNumber(
                            SnailfishElement(floor(halfValue).toLong()),
                            SnailfishElement(ceil(halfValue).toLong())
                        )
                    )
                } else {
                    this
                }
            } else {
                SnailfishElement(element = this.element!!.split())
            }
        }

        override fun toString(): String {
            return value?.toString() ?: element.toString()
        }
    }
}