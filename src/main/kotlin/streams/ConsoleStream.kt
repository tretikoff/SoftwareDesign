package streams

open class ConsoleStream : Stream() {
    override fun read(): String? {
        return readLine()
    }

    override fun write(statement: String?) {
        print(statement)
    }
}
