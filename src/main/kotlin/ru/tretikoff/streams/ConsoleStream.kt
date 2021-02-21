package ru.tretikoff.streams

/**
 * Stream in which information is read from the console and written to the console
 */
open class ConsoleStream : Stream() {
    override fun read(): String? {
        return readLine()
    }

    override fun write(statement: String?) {
        print(statement)
    }
}
