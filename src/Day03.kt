data class Position(val x: Int, val y: Int)
data class Number(val number: Int, val positions: List<Position>)
fun main() {
    val directions = arrayOf(
        Pair(-1, -1), Pair(-1, 0), Pair(-1, 1),
        Pair(0, -1), /* current */ Pair(0, 1),
        Pair(1, -1), Pair(1, 0), Pair(1, 1)
    )

    fun getNumbersWithIdx(schema: List<String>): List<Number> {
        return schema.flatMap { line ->
            var i = 0
            val rowIdx = schema.indexOf(line)
            val originalNumbersWithIdx = line
                .withIndex()
                .filter { it.value.isDigit() }

            val numbersOnThisLine: List<Number> = Regex("[0-9]+")
                .findAll(line)
                .map{ it.value }
                .toList()
                .map { number ->
                    val positionsOfIndiviualDigits = (number.indices).map { i -> Position(rowIdx, line.indexOf(number) + i) }
                    Number(number.toInt(), positionsOfIndiviualDigits)
                }

            val numbersWithIdx = numbersOnThisLine.map {number ->
                val positions = number.positions.map {
                    val newPosition = Position(it.x, originalNumbersWithIdx[i].index)
                    i += 1
                    newPosition
                }
                Number(number.number, positions)
            }

            numbersWithIdx
        }
    }

    fun checkIfAdjacentIsSymbol(schema: List<String>, numbers: Number): Boolean {
        val rowBounds = schema.indices
        val colBounds = schema[0].indices

        for (number in numbers.positions) {
            for ((x, y) in directions) {
                val neighbour = Position(number.x + x, number.y + y)

                if (neighbour.x in rowBounds && neighbour.y in colBounds) {
                    val symbol = schema[neighbour.x][neighbour.y]
                    if (symbol != '.' && !symbol.isDigit())
                        return true
                }
            }
        }
        return false
    }

    fun getNeighbourDigitPositions(schema: List<String>, gear: Position): List<Position> {
        val rowBounds = schema.indices
        val colBounds = schema[0].indices
        val listOfDigitsPositions = mutableListOf<Position>()

        for ((x, y) in directions) {
            val neighbour = Position(gear.x + x, gear.y + y)

            if (neighbour.x in rowBounds && neighbour.y in colBounds) {
                val symbol = schema[neighbour.x][neighbour.y]
                if (symbol.isDigit())
                    listOfDigitsPositions.add(neighbour)
            }
        }
        return listOfDigitsPositions
    }

    fun part1(schema: List<String>): Int = getNumbersWithIdx(schema).filter { checkIfAdjacentIsSymbol(schema, it) }.sumOf { it.number }

    fun part2(schema: List<String>): Int {
        val allNumbersWithIdx = getNumbersWithIdx(schema)

        val gears = schema
            .flatMap { line ->
                val rowIdx = schema.indexOf(line)
                val positions = line
                    .withIndex()
                    .filter { it.value == '*' }
                    .map { Position(rowIdx, it.index) }

                positions
            }
            .map { getNeighbourDigitPositions(schema, it) }
            .filter { it.size > 1 }

        val digitsNextToGears = gears
            .map { listOfPos ->
                allNumbersWithIdx
                    .filter{ it.positions.intersect(listOfPos).isNotEmpty() }
                    .map { it.number }
            }
            .filter{ it.size == 2 }
            .map { it.reduce { acc, i -> acc * i} }

        return digitsNextToGears.sum()
    }

//     test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03-Example")
    check(part1(testInput) == 4361)
    check(part2(testInput) == 467835)

    val input = readInput("Day03")
    println("Day 03 - Part one: ${part1(input)}")
    println("Day 03 - Part two: ${part2(input)}")
}