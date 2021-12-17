package problems

class Day16(override val input: String) : Problem {
    override val number: Int = 16

    override fun runPartOne(): String {
        return BITS.fromString(input)
            .packets
            .first()
            .versionSum()
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

            fun versionSum(): Int
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

            override fun versionSum(): Int {
                return this.version + this.packets.sumOf { it.versionSum() }
            }
        }

        data class LiteralValue(override val version: Int, val value: Long) : Packet {
            override val typeId: Int = 4

            override fun versionSum(): Int {
                return version
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