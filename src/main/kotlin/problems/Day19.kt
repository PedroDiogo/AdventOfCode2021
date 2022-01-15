package problems

import java.util.*
import kotlin.math.*

typealias Point = Triple<Int, Int, Int>
typealias Distance = Triple<Int, Int, Int>
typealias RotationFunction = (Triple<Int, Int, Int>) -> Triple<Int, Int, Int>

class Day19(override val input: String) : Problem {
    override val number: Int = 19
    private val beaconsSeenFromScanner: Map<Int, List<Point>> =
        input.split("\n\n").associate { scanner ->
            val scannerNumber = """\d+""".toRegex()
                .find(scanner.lines().first())!!
                .groupValues
                .single()
                .toInt()

            scannerNumber to scanner.lines().drop(1).map { beaconCoords ->
                val (x, y, z) = beaconCoords.split(",").map { it.toInt() }
                Triple(x, y, z)
            }
        }
    private val numberOfScanners = beaconsSeenFromScanner.keys.maxOf { it }
    private val possibleRotations = listOf(0.0, PI / 2, PI, 3 * PI / 2)
    private val rotationFunctions = possibleRotations.flatMap { xRotation ->
        possibleRotations.flatMap { yRotation ->
            possibleRotations.map { zRotation ->
                { a: Point -> a.rotateZ(zRotation).rotateY(yRotation).rotateX(xRotation) }
            }
        }
    }

    private val connections:MutableMap<Int, MutableMap<Int, Pair<Point, (Point) -> Point>>> by lazy {findScannerConnections()}

    override fun runPartOne(): String {
        val connectionGraph = connections.map { (key, values) -> key to values.keys.toList() }.toMap()

        val prev = dijkstra(connectionGraph, 0)
        val beacons = mutableSetOf<Point>()
        beacons.addAll(beaconsSeenFromScanner[0]!!)

        for (source in 1..numberOfScanners) {
            val pathToRoot = path(prev, source).reversed()

            val toBeacons = pathToRoot.zipWithNext().fold(beaconsSeenFromScanner[source]!!) { acc, (iterFrom, iterTo) ->
                val (iterOrigin, iterRotationFunction) = connections[iterTo]!![iterFrom]!!
                translateCoordinates(acc, iterOrigin, iterRotationFunction)
            }
            beacons.addAll(toBeacons)
        }

        return beacons.size.toString()
    }

    override fun runPartTwo(): String {
        val connectionGraph = connections.map { (key, values) -> key to values.keys.toList() }.toMap()

        val prev = dijkstra(connectionGraph, 0)
        val scannersOrigins = mutableMapOf<Int, Point>()
        scannersOrigins[0] = Point(0,0,0)

        for (source in 1..numberOfScanners) {
            val pathToRoot = path(prev, source).reversed()

            val toBeacons = pathToRoot.zipWithNext().fold(Point(0,0,0)) { acc, (iterFrom, iterTo) ->
                val (iterOrigin, iterRotationFunction) = connections[iterTo]!![iterFrom]!!
                translateCoordinates(listOf(acc), iterOrigin, iterRotationFunction).single()
            }
            scannersOrigins[source] = toBeacons
        }

        var maxDistance = -1
        for (scannerOne in 0..numberOfScanners) {
            for (scannerTwo in scannerOne+1..numberOfScanners) {
                val distance = scannersOrigins[scannerOne]!!.manhattanDistance(scannersOrigins[scannerTwo]!!)
                if (distance > maxDistance) {
                    maxDistance = distance
                }
            }
        }

        return maxDistance.toString()
    }

    private fun findScannerConnections(): MutableMap<Int, MutableMap<Int, Pair<Point, (Point) -> Point>>> {
        val connections = mutableMapOf<Int, MutableMap<Int, Pair<Point, (Point) -> Point>>>()
        (0..numberOfScanners).forEach{scanner ->
            connections[scanner] = mutableMapOf()
        }

        for (scannerOne in 0..numberOfScanners) {
            for (scannerTwo in scannerOne + 1..numberOfScanners) {
                val overlap = overlapScanners(scannerOne, scannerTwo)
                if (overlap != null) {
                    connections.computeIfAbsent(scannerOne) { mutableMapOf() }
                    connections.computeIfAbsent(scannerTwo) { mutableMapOf() }
                    connections[scannerOne]!![scannerTwo] = overlap
                    connections[scannerTwo]!![scannerOne] = overlapScanners(scannerTwo, scannerOne)!!
                }
            }
        }
        return connections
    }

    fun dijkstra(connections: Map<Int, List<Int>>, source: Int): MutableList<Int> {
        val distance = MutableList<Long>(numberOfScanners + 1) { Int.MAX_VALUE.toLong() }
        val prev = MutableList(numberOfScanners + 1) { -1 }
        distance[source] = 0

        val queue = PriorityQueue<Int>(compareBy { distance[it] })
        queue.addAll(connections.keys)

        while (queue.isNotEmpty()) {
            val u = queue.poll()!!

            connections[u]!!.forEach { neighbour ->
                val alt = distance[u] + 1
                if (alt < distance[neighbour]) {
                    distance[neighbour] = alt
                    prev[neighbour] = u
                    if (queue.remove(neighbour)) {
                        queue.add(neighbour)
                    }
                }
            }
        }

        return prev
    }

    fun path(prev: List<Int>, to: Int): List<Int> {
        val path = mutableListOf<Int>()
        var current = to
        if (prev[current] != -1) {
            while (current != -1) {
                path.add(0, current)
                current = prev[current]
            }
        }
        return path
    }

    fun translateCoordinates(points: List<Point>, oldOrigin: Point, rotationFunction: RotationFunction): List<Point> {
        return points.map(rotationFunction).map { it + oldOrigin }
    }

    fun overlapScanners(
        originScannerIdx: Int,
        otherScannerIdx: Int
    ): Pair<Point, RotationFunction>? {
        val originScannerDistances = calculateDistances(beaconsSeenFromScanner[originScannerIdx]!!)

        for (rotationFunction in rotationFunctions) {
            val otherScannerBeaconsPostRotation = beaconsSeenFromScanner[otherScannerIdx]!!.map(rotationFunction)
            val otherScannerDistances = calculateDistances(otherScannerBeaconsPostRotation)
            val distanceIntersection = intersectDistances(originScannerDistances, otherScannerDistances)

            val a = distanceIntersection.indices.map { beaconIdx ->
                beaconsSeenFromScanner[originScannerIdx]!![distanceIntersection[beaconIdx].first] - rotationFunction(
                    beaconsSeenFromScanner[otherScannerIdx]!![distanceIntersection[beaconIdx].second]
                )
            }.toSet()

            if (a.size == 1) {
                return Pair(a.single(), rotationFunction)
            }
        }
        return null
    }

    fun calculateDistances(beacons: List<Point>): List<List<Distance>> {
        return beacons.map { first ->
            beacons.map { second ->
                first - second
            }
        }
    }

    fun intersectDistances(
        scannerA: List<List<Distance>>,
        scannerB: List<List<Distance>>
    ): List<Pair<Int, Int>> {
        return (scannerA.indices).flatMap { a -> scannerB.indices.map { b -> Pair(a, b) } }
            .map { Pair(it, scannerA[it.first].toSet().intersect(scannerB[it.second].toSet())) }
            .filter { it.second.size >= 12 }
            .map { it.first }
    }

    operator fun Point.minus(other: Point): Point {
        return Point(first - other.first, second - other.second, third - other.third)
    }

    operator fun Point.plus(other: Point): Point {
        return Point(first + other.first, second + other.second, third + other.third)
    }

    // Rotation formulas
    // https://www.mathworks.com/matlabcentral/answers/123763-how-to-rotate-entire-3d-data-with-x-y-z-values-along-a-particular-axis-say-x-axis#comment_487621
    fun Point.rotateX(angle: Double): Point {
        val (x, y, z) = this
        val newX = x
        val newY = (y * cos(angle) - z * sin(angle)).roundToInt()
        val newZ = (y * sin(angle) + z * cos(angle)).roundToInt()
        return Point(newX, newY, newZ)
    }

    fun Point.rotateY(angle: Double): Point {
        val (x, y, z) = this
        val newX = (x * cos(angle) + z * sin(angle)).roundToInt()
        val newY = y
        val newZ = (z * cos(angle) - x * sin(angle)).roundToInt()
        return Triple(newX, newY, newZ)
    }

    fun Point.rotateZ(angle: Double): Point {
        val (x, y, z) = this
        val newX = (x * cos(angle) - y * sin(angle)).roundToInt()
        val newY = (x * sin(angle) + y * cos(angle)).roundToInt()
        val newZ = z
        return Triple(newX, newY, newZ)
    }

    fun Point.manhattanDistance(other: Point) : Int {
        val difference = this-other
        return difference.first.absoluteValue + difference.second.absoluteValue + difference.third.absoluteValue
    }
}