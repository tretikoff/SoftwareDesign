package commands

import streams.Stream
import kotlin.NotImplementedError

open class Command(
    val inputStream: Stream,
    val outputStream: Stream,
    val errorStream: Stream,
    val args: List<String>,
    val kwargs: Map<String, String>,
) {
    open fun execute(): Int {
        throw NotImplementedError()
    }
}