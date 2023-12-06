fun main() {
    fun calculateWins(game: Pair<Long, Long>): Long {
        var waysToWin = 0.toLong()

        for (i in 0..game.first) {
            val distance = (game.first - i) * i
            if (distance > game.second) {
                waysToWin++
            }
        }

        return waysToWin
    }

    fun part1(input: List<String>): Long {
        val parsed = input
            .map { it.split(":", " ").filter { it.isNotEmpty() }.drop(1).map { it.toLong() } }

        val games = parsed.first().zip(parsed.last())
        var totalWaysToWin = 1.toLong()

        for (game in games) {
            val totalWins = calculateWins(game)
            if (totalWins > 0)
                totalWaysToWin *= totalWins
        }
        return totalWaysToWin
    }

    fun part2(input: List<String>): Long {
        val game = input
            .map { it.split(":", " ").drop(1).filter{ it.isNotEmpty() } }.map { it.joinToString("") }.map { it.toLong() }

        return calculateWins(Pair(game[0], game[1]))
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06-Example")
    check(part1(testInput) == 288.toLong())
    check(part2(testInput) == 71503.toLong())

    val input = readInput("Day06")
    println("Day 06 - Part one: ${part1(input)}")
    println("Day 06 - Part two: ${part2(input)}")
}