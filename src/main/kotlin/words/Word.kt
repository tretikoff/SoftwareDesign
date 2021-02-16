package words

import java.util.logging.Logger

enum class QuotationType {
    None,
    SingleQuoted,
    DoubleQuoted,
}

private val logger = Logger.getLogger(Word::class.java.name)

/**
 * An abstraction of word token in the bash syntax
 */
class Word(var value: String, var quotationType: QuotationType = QuotationType.None) {
    fun substituteVariables(variables: Map<String, String>) {
        while (true) {
            val regex = "\\$\\w+".toRegex()
            val match = regex.find(value) ?: return
            val replacement = variables[match.value.drop(1)] ?: ""
            logger.info("replacing ${match.value} with $replacement in $value")
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
            words.add(Word(word, quotationType))
        }
        var end = if (quote != null) rest.indexOf(quote, start + 1) else -1
        if (end == -1) end = rest.length
        val word = rest.substring(start + 1, end).trim()
        words.add(Word(word, quotationType))
        rest = rest.substring(end)
    }
    return words
}

fun splitBySpaces(statement: String): List<Word> {
    return statement.split(" ").map { x -> Word(x) }
}
