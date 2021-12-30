package problems

class Day24(override val input: String) : Problem {
    override val number: Int = 24

    // Solved in a spreadsheet by hand
    // https://docs.google.com/spreadsheets/d/1riyoG2z5hstnSvbAtaT7f_P4pnMgvpqC8toN468lCmQ/edit?usp=sharing

    override fun runPartOne(): String {
        val programInput = "91599994399395"
        val result = runProgram(programInput)
        return if (result['z'-'w'] == 0) {
            programInput
        } else {
            "Invalid Program Input"
        }
    }

    override fun runPartTwo(): String {
        val programInput = "71111591176151"
        val result = runProgram(programInput)
        return if (result['z'-'w'] == 0) {
            programInput
        } else {
            "Invalid Program Input"
        }
    }

    private fun runProgram(programInput: String) : IntArray {
        val registers = intArrayOf(0, 0, 0, 0)
        var inputIdx = 0

        for (line in input.lines()) {
            val splitLine = line.split(" ")
            val destRegister = splitLine[1][0]-'w'
            val secondParam = when {
                splitLine.getOrNull(2) == null -> null
                splitLine[2].toIntOrNull() == null -> registers[splitLine[2][0]-'w']
                splitLine[2].toIntOrNull() != null -> splitLine[2].toInt()
                else -> null
            }

            registers[destRegister] = when (splitLine[0]) {
                "inp" -> programInput[inputIdx++].digitToInt()
                "add" -> registers[destRegister] + secondParam!!
                "mul" -> registers[destRegister] * secondParam!!
                "div" -> registers[destRegister] / secondParam!!
                "mod" -> registers[destRegister] % secondParam!!
                "eql" -> when(registers[destRegister] == secondParam!!) { true -> 1; false -> 0}
                else -> registers[destRegister]
            }
        }
        return registers
    }

}