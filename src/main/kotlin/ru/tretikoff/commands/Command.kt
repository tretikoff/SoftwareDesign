package ru.tretikoff.commands

import ru.tretikoff.streams.Stream

/**
 * Abstract command, which takes three streams, list of args and map of named args
 * @param inputStream - stream for command to read from
 * @param outputStream - stream fot the command to write to
 * @param errorStream - stream for the command to write errors to
 * @param args - command arguments
 */
abstract class Command(
    val inputStream: Stream,
    val outputStream: Stream,
    val errorStream: Stream,
    val args: MutableList<String>,
) {
    abstract fun execute(): Int
    var flags: MutableMap<String, Boolean> = mutableMapOf()
}
