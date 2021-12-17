package problems

class Day16(override val input: String) : Problem {
    override val number: Int = 16
    private val packets = BITS.fromString(input).packets

    override fun runPartOne(): String {
        return packets
            .first()
            .packetVersion()
            .toString()
    }

    override fun runPartTwo(): String {
        return packets
            .first()
            .packetValue()
            .toString()
    }

    class BITS(val packets: List<Packet>) {
        companion object {
            fun fromString(str: String): BITS {
                val packets = mutableListOf<Packet>()
                val binary = str.convertToBinary()
                var start = 0
                while (!binary.substring(start).isPadding()) {
                    val (packet, read) = createPacket(binary.substring(start))
                    packets.add(packet)
                    start += read
                }
                return BITS(packets)
            }

            private fun String.convertToBinary(): String {
                return this
                    .toCharArray()
                    .joinToString(separator = "") { it.digitToInt(16).toString(2).padStart(4, '0') }
            }

            private fun String.isPadding(): Boolean {
                return this.matches("""0*""".toRegex())
            }


            private fun createPacket(str: String): Pair<Packet, Int> {
                val typeId = str.substring(3, 6).toInt(2)
                return when (typeId) {
                    4 -> LiteralValue.fromString(str)
                    else -> Operator.fromString(str)
                }
            }
        }

        interface Packet {
            val version: Int
            val typeId: Int

            fun packetVersion(): Int
            fun packetValue(): Long
        }

        data class Operator(override val version: Int, override val typeId: Int, val packets: List<Packet>) :
            Packet {
            companion object {
                fun fromString(str: String): Pair<Operator, Int> {
                    val version = str.substring(0, 3).toInt(2)
                    val typeId = str.substring(3, 6).toInt(2)
                    val lengthTypeId = str.substring(6, 7)
                    val (packets, read) = when (lengthTypeId) {
                        "0" -> createPacketsTotalLength(str.substring(7))
                        else -> createPacketsNumberOfPackets(str.substring(7))
                    }
                    return Pair(Operator(version, typeId, packets), 7 + read)
                }

                private fun createPacketsNumberOfPackets(str: String): Pair<List<Packet>, Int> {
                    val numberOfPackets = str.substring(0, 11).toInt(2)
                    val packets = mutableListOf<Packet>()
                    var read = 11

                    for (packetNo in 0 until numberOfPackets) {
                        val (newPacket, packetRead) = BITS.createPacket(str.substring(read))
                        packets.add(newPacket)
                        read += packetRead
                    }
                    return Pair(packets, read)
                }

                private fun createPacketsTotalLength(str: String): Pair<List<Packet>, Int> {
                    val totalLength = str.substring(0, 15).toInt(2)
                    val packets = mutableListOf<Packet>()
                    var read = 15

                    while (read - 15 < totalLength) {
                        val (newPacket, packetRead) = BITS.createPacket(str.substring(read))
                        packets.add(newPacket)
                        read += packetRead
                    }
                    return Pair(packets, read)
                }
            }

            override fun packetVersion(): Int {
                return this.version + this.packets.sumOf { it.packetVersion() }
            }

            override fun packetValue(): Long {
                val packetValues = packets.map { packet -> packet.packetValue() }
                return when (typeId) {
                    0 -> packetValues.sumOf { it }
                    1 -> packetValues.fold(1) { mul, value -> mul * value }
                    2 -> packetValues.minOf { it }
                    3 -> packetValues.maxOf { it }
                    5 -> when (packetValues[0] > packetValues[1]) {
                        true -> 1; else -> 0
                    }
                    6 -> when (packetValues[0] < packetValues[1]) {
                        true -> 1; else -> 0
                    }
                    7 -> when (packetValues[0] == packetValues[1]) {
                        true -> 1; else -> 0
                    }
                    else -> 0L
                }
            }
        }

        data class LiteralValue(override val version: Int, val value: Long) : Packet {
            override val typeId: Int = 4

            override fun packetVersion(): Int {
                return version
            }

            override fun packetValue(): Long {
                return value
            }

            companion object {
                fun fromString(str: String): Pair<LiteralValue, Int> {
                    val version = str.substring(0, 3).toInt(2)
                    var read = 6
                    var lastGroup = false
                    val valueStrBuilder = StringBuilder()
                    while (!lastGroup) {
                        lastGroup = str.substring(read, read + 1) == "0"
                        valueStrBuilder.append(str.substring(read + 1, read + 5).toInt(2).toString(16))
                        read += 5
                    }

                    val value = valueStrBuilder.toString().toLong(16)
                    return Pair(LiteralValue(version, value), read)
                }
            }
        }
    }
}