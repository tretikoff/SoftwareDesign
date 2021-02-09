package streams

open class Stream {
    open fun read(): String? {
        throw NotImplementedError()
    }

    open fun write(statement: String?) {
        throw NotImplementedError()
    }

    open fun writeLine(statement: String?) {
        write(statement)
        write("\n")
    }
}