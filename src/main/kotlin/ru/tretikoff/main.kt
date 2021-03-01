package ru.tretikoff

import ru.tretikoff.streams.ConsoleStream
import ru.tretikoff.streams.Stream

private val consoleStream: Stream = ConsoleStream()
private val descriptors = mutableListOf(consoleStream, consoleStream, consoleStream)


/**
 * The ru.tretikoff.main flow of bash
 */
fun main() {
    val shell = Shell(descriptors)
    shell.run()
}
