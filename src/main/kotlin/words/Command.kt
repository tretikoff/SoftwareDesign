package words

import java.util.logging.Logger

enum class QuotationType {
    None,
    SingleQuoted,
    DoubleQuoted,
}

private val logger = Logger.getLogger(Word::class.java.name)

open class Word(var value: String, var quotationType: QuotationType = QuotationType.None) {
    fun substituteVariables(variables: Map<String, String>) {
        while (true) {
            val regex = "\\$\\w+".toRegex()
            val match = regex.find(value) ?: return
            val replacement = variables[match.value.drop(1)] ?: ""
            logger.finest("replacing ${match.value} with $replacement in $value")
            value = value.replace(regex, replacement)
        }
    }

    fun isPipe(): Boolean {
        return value == "|"
    }
}

/**
 * Splits statement by quote
 */
fun splitByQuotes(statement: String): List<Word> {
    var rest = statement
    val words = mutableListOf<Word>()
    while (rest.isNotBlank()) {
        val singleStart = rest.indexOf('\'')
        val doubleStart = rest.indexOf('"')
        var quotationType: QuotationType
        var quote: Char
        var start: Int
        if (singleStart < doubleStart) {
            quotationType = QuotationType.SingleQuoted
            quote = '\''
            start = singleStart
        } else {
            quotationType = QuotationType.DoubleQuoted
            quote = '"'
            start = doubleStart
        }
        var end = rest.indexOf(quote, start)
        if (end == -1) end = rest.length
        val word = rest.substring(start, end).trim()
        words.add(Word(word, quotationType))
        rest = rest.substring(end)
    }
    return words
}

fun splitBySpaces(statement: String): List<Word> {
    return statement.split(" ").map { x -> Word(x) }
}
