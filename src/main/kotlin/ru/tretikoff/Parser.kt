package ru.tretikoff

import ru.tretikoff.words.QuotationType
import ru.tretikoff.words.Word
import java.util.logging.Logger

class Parser {
    private val logger = Logger.getLogger(Parser::class.java.name)
    fun parse(statement: String): List<Word> {
        val statements = mutableListOf<Word>()
        val words = splitByQuotes(statement)
        logger.finest("Splitted $statement by quotes ${words.map { it.value }}")
        for (word in words) {
            if (word.quotationType == QuotationType.None) {
                statements.addAll(splitBySpaces(word.value))
            } else {
                statements.add(word)
            }
        }
        logger.finest("Splitted $statement into ${statements.map { it.value }}")
        return statements
    }

    private fun splitByQuotes(statement: String): List<Word> {
        var rest = statement
        val words = mutableListOf<Word>()
        while (rest.isNotBlank() && rest != "'" && rest != "\"") {
            var singleStart = rest.indexOf('\'')
            if (singleStart == -1) {
                singleStart = Int.MAX_VALUE
            }
            var doubleStart = rest.indexOf('"')
            if (doubleStart == -1) {
                doubleStart = Int.MAX_VALUE
            }
            var quotationType: QuotationType
            var quote: Char? = null
            var start: Int
            when {
                singleStart < doubleStart -> {
                    quotationType = QuotationType.SingleQuoted
                    quote = '\''
                    start = singleStart
                }
                doubleStart < singleStart -> {
                    quotationType = QuotationType.DoubleQuoted
                    quote = '"'
                    start = doubleStart
                }
                else -> {
                    start = -1
                    quotationType = QuotationType.None
                }
            }
            if (start != -1) {
                val word = rest.substring(0, start).trim()
                if (word.isNotEmpty()) {
                    words.add(Word(word, QuotationType.None))
                }
            }
            var end = if (quote != null) rest.indexOf(quote, start + 1) else -1
            logger.finest("parsing $rest from the $start to the $end")
            if (end == -1) end = rest.length
            val word = rest.substring(start + 1, end).trim()
            words.add(Word(word, quotationType))
            rest = rest.substring(end)
        }
        return words
    }

    private fun splitBySpaces(statement: String): List<Word> {
        return statement.split(" ").map { x -> Word(x) }
    }
}
