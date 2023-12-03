fun main() {
    val words = mapOf(
        "one" to 1,
        "two" to 2,
        "three" to 3,
        "four" to 4,
        "five" to 5,
        "six" to 6,
        "seven" to 7,
        "eight" to 8,
        "nine" to 9,
        "1" to 1,
        "2" to 2,
        "3" to 3,
        "4" to 4,
        "5" to 5,
        "6" to 6,
        "7" to 7,
        "8" to 8,
        "9" to 9,
    )

    fun part2(calibrations: List<String>): Int {
        return calibrations
            .fold(0) { acc, str ->
                val firstDigit = words[str.findAnyOf(words.keys)?.second]
                val lastDigit = words[str.findLastAnyOf(words.keys)?.second]

                acc + (firstDigit!! * 10) + lastDigit!!
            }
    }

    fun part1(calibrations: List<String>): Int {
        return calibrations
            .fold(0) {
                    acc, str ->
                val firstDigit = str.first { char -> char.isDigit() }.digitToInt()
                val lastDigit = str.last { char -> char.isDigit() }.digitToInt()

                acc + (firstDigit * 10) + lastDigit
            }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01-Example")
    check(part1(testInput) == 142)

    val calibrations = readInput("Day01")
    part1(calibrations).println()

    println("Day 01 - Part one: ${part1(calibrations)}")
    println("Day 01 - Part two: ${part2(calibrations)}")
}


