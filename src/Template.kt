import kotlin.system.measureTimeMillis

fun main() {
    fun part1(input: List<String>): Int {
        return input.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01-Example")
    check(part1(testInput) == 1)

    val input = readInput("Day01")
    println("Day 00 - Part one: ${part1(input)}")
    println("Day 00 - Part two: ${part2(input)}")

//    val timeInMillis = measureTimeMillis {}
}