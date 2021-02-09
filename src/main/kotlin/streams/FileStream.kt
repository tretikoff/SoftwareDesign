package streams

import java.io.File

open class FileStream(filename: String): Stream() {
    private val file = File(filename);
    override fun read(): String? {
        val bs = file.inputStream().readAllBytes()
        return String(bs)
    }

    override fun write(statement: String) {
        file.outputStream().write(statement.toByteArray());
    }
}