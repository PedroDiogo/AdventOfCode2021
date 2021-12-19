package problems

import kotlin.math.ceil
import kotlin.math.floor

class Day18(override val input: String) : Problem {
    override val number: Int = 18

    override fun runPartOne(): String {
        return input
            .lines()
            .map { SnailfishNumber.fromString(it) }
            .reduce { acc, snailfishNumber -> (acc + snailfishNumber).reduce() }
            .magnitude()
            .toString()
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
                previousNumber = currentNumber
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
            val (leftRemainder, rightRemainder) = this.toString().substring(leftIdx + 1, rightIdx).split(",")
                .map { it.toLong() }
            val leftString = this.toString().substring(0, leftIdx)
            val rightString = this.toString().substring(rightIdx + 1)

            var findPairLeft = """\d+""".toRegex().find(leftString)
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

            val findPairRight = """\d+""".toRegex().find(rightString)
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