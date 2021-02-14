package streams

import java.io.File

open class FileStream(filename: String) : Stream() {
    private val file = File(filename)
    private val br = file.inputStream().bufferedReader()
    override fun read(): String? {
        return br.readLine()
    }

    override fun write(statement: String?) {
        if (statement != null) {
            file.outputStream().write(statement.toByteArray())
        }
    }
}
