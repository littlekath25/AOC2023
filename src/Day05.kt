fun main() {
    data class Seed(val start: Long, var location: Long)
    data class Range(val destStart: Long, val srcStart: Long, val srcEnd: Long)
    data class Category(val ranges: MutableList<Range>)

    fun getSeeds(input: List<String>): List<Seed> {
        return input
            .first()
            .split(":")[1]
            .split(" ")
            .filter { it.isNotEmpty() }
            .map { Seed(it.toLong(), it.toLong()) }
    }

    fun getSeedRanges(input: List<String>): List<LongRange> {
        return input
            .first()
            .split(":")[1]
            .split(" ")
            .filter { it.isNotEmpty() }
            .chunked(2)
            .map {
                val start = it.first().toLong()
                val end = it.first().toLong() + it.last().toLong() - 1
                LongRange(start, end)
            }
    }

    fun getCategories(input: List<String>): List<Category> {
        val categories: MutableList<Category> = emptyList<Category>().toMutableList()

        for (line in input) {
            if (line.contains("[0-9]".toRegex())) {
                val (dest, src, range) = line.split(" ").map { it.toLong() }
                categories.last().ranges.add(Range(dest, src, src + range))
            }
            else
                categories.add(Category(emptyList<Range>().toMutableList()))
        }
        return categories
    }

    fun getCategoriesReversed(input: List<String>): List<Category> {
        val categories: MutableList<Category> = emptyList<Category>().toMutableList()

        for (line in input) {
            if (line.contains("[0-9]".toRegex())) {
                val (src, dest, range) = line.split(" ").map { it.toLong() }
                categories.last().ranges.add(Range(dest, src, src + range))
            }
            else
                categories.add(Category(emptyList<Range>().toMutableList()))
        }

        return categories.reversed()
    }

    fun getDigit(number: Long, range: Range): Long {
        val offset = number - range.srcStart
        return range.destStart + offset
    }

    fun getDigitReversed(number: Long, range: Range): Long {
        val offset = number - range.srcStart
        return range.destStart + offset
    }

    fun part1(input: List<String>): Long {
        val seeds = getSeeds(input)
        val categories = getCategories(input.drop(1).filter { it.isNotEmpty() })

        for (seed in seeds) {
            for (category in categories) {
                val range = category.ranges.filter { seed.location in it.srcStart..< it.srcEnd }
                if (range.isNotEmpty())
                    seed.location = getDigit(seed.location, range.first())
            }
        }
        return seeds.minBy{ it.location }.location
    }

    fun part2(input: List<String>): Long {
        val seedRanges = getSeedRanges(input)
        val categories = getCategoriesReversed(input.drop(1).filter { it.isNotEmpty() })

        var seed: Long

        for (location in 0..Long.MAX_VALUE) {
            seed = location
            for (category in categories) {
                val range = category.ranges.filter { seed in it.srcStart..<it.srcEnd }
                if (range.isNotEmpty())
                    seed = getDigitReversed(seed, range.first())
            }

            for (seedRange in seedRanges) {
                if (seed in seedRange) {
                    return location
                }
            }

        }
        return -1
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05-Example")
    check(part1(testInput) == 35.toLong())
    check(part2(testInput) == 46.toLong())

    val input = readInput("Day05")
    println("Day 05 - Part one: ${part1(input)}")
    println("Day 05 - Part two: ${part2(input)}") // Takes 99856 ms HAHAH
}