package commands

import streams.Stream

/**
 * Abstract command, which takes three streams, list of args and map of named args
 */
abstract class Command(
    val inputStream: Stream,
    val outputStream: Stream,
    val errorStream: Stream,
    val args: List<String>,
    val kwargs: Map<String, String>,
) {
    abstract fun execute(): Int
}
