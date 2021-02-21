package ru.tretikoff.streams

import ru.tretikoff.NoSuchFileOrDirectoryException
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.lang.NullPointerException

/**
 * Creates a new FileStream from a
 * @param filename - name of the file to read/write information
 * @throws NoSuchFileException if the file does not exist or filename is null
 */
@Suppress("TooGenericExceptionCaught", "SwallowedException")
open class FileStream(filename: String) : Stream() {
    private val file: File
    private val outputStream: FileOutputStream
    private val br: BufferedReader

    init {
        try {
            file = File(filename)
            outputStream = FileOutputStream(file, true)
            br = file.inputStream().bufferedReader()
        } catch (e: FileNotFoundException) {
            throw NoSuchFileOrDirectoryException(filename)
        } catch (e: NullPointerException) {
            throw NoSuchFileOrDirectoryException(filename)
        }
    }

    override fun read(): String? {
        return br.readLine()
    }

    override fun write(statement: String?) {
        if (statement != null) {
            outputStream.write(statement.toByteArray())
        }
    }
}
