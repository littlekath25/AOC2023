fun main() {
    fun part2(games: List<Map<String, Int>>): Int {
        return games.fold(0) { acc, game ->
            acc + (game["red"]!! * game["green"]!! * game["blue"]!!)
        }
    }

    fun part1(games: List<Map<String, Int>>): Int {
        return games.fold(0) { acc, game ->
            if (game["red"]!! <= 12 && game["green"]!! <= 13 && game["blue"]!! <= 14)
                acc + game["Game"]!!
            else
                acc
        }
    }

    fun formatInput(input: List<String>): List<Map<String, Int>> {
        return input
            .map { line -> line.split(":", ";").flatMap { it.split(", ") }.map { it.split(" ").filter{ !it.isEmpty() } } }
            .map { game ->
                game.map { if (it.first() != "Game") it.reversed() else it }
                    .groupBy { it.first() }
                    .map { it.key to it.value.flatten().filter{ number -> number != it.key }.map { it.toInt() }.max() }.toMap()
            }
    }

    val testGames = formatInput(readInput("Day02-Example").toMutableList())
    val games = formatInput(readInput("Day02").toMutableList())

//     TEST FOR EXAMPLE INPUT
    check(part1(testGames) == 8)
    check(part2(testGames) == 2286)

    println("Day 02 - Part one: ${part1(games)}")
    println("Day 02 - Part two: ${part2(games)}")
}


