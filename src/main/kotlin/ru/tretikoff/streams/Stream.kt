package ru.tretikoff.streams

abstract class Stream {
    abstract fun read(): String?

    abstract fun write(statement: String?)

    open fun writeLine(statement: String?) {
        write(statement)
        write("\n")
    }
}
