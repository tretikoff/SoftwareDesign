package ru.tretikoff.words

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
        logger.finest("sadg")
        while (true) {
            val regex = "\\$\\w+".toRegex()
            val match = regex.find(value) ?: return
            val replacement = variables[match.value.drop(1)] ?: ""
            logger.finest("replacing ${match.value} with $replacement in $value")
            value = value.replaceRange(match.range, replacement)
        }
    }

    fun isKey(): Boolean {
        return value.startsWith('-')
    }

    fun isPipe(): Boolean {
        return value == "|"
    }
}
