import kotlin.math.pow

fun main() {
    data class Card(val game: Int, val winningNumbers: List<Int>, val numbers: MutableList<Int>)
    data class CardGame(val game: Int, val winningNumbers: Int, var total: Int)

    fun createCards(input: List<String>): List<Card> {
        return input.map { line ->
            val splitString = line.split(":", "|").map{ it.trim() }
            val winningNumbers = splitString[1].split(" ").filter{ it.isNotEmpty() }.map{ it.toInt() }.toList()
            val numbers = splitString[2].split(" ").filter{ it.isNotEmpty() }.map{ it.toInt() }.toMutableList()
            val game = splitString[0].split(" ").last().toInt()
            Card(game, winningNumbers, numbers)
        }
    }

    fun part1(cards: List<Card>): Int {
        val cardsWithWinningNumbers = cards.map {
            val total = it.winningNumbers.intersect(it.numbers.toSet()).size
            if (total > 0)
                2.0.pow((total - 1).toDouble())
            else
                0
        }
        return cardsWithWinningNumbers.sumOf { it.toInt() }
    }

    fun part2(originalCards: List<Card>): Int {
        val scratchCards = originalCards.map { CardGame(it.game, it.winningNumbers.intersect(it.numbers.toSet()).size, 1)}

        for (scratchCard in scratchCards) {
            val cardsToCopy = (scratchCard.game + 1 .. scratchCard.game + scratchCard.winningNumbers).toList()

            if (cardsToCopy.isNotEmpty()) {
                scratchCards.forEach {card ->
                    if (card.game in cardsToCopy) {
                        card.total += scratchCard.total
                    }
                }
            }
        }

        return scratchCards.sumOf { it.total }
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04-Example")
    val testCards = createCards(testInput)

    check(part1(testCards) == 13)
    check(part2(testCards) == 30)

    val input = readInput("Day04")
    val cards = createCards(input)

    println("Day 04 - Part one: ${part1(cards)}")
    println("Day 04 - Part two: ${part2(cards)}")
}