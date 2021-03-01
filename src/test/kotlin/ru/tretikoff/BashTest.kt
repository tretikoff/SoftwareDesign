package ru.tretikoff

import java.io.File

open class BashTest {
    val fileContent = listOf("first line", "second line", "", "fourth line")
    val fileName = "test"
    val tempDir = createTempDir("tempTestDir")
    val tempFile = File.createTempFile(fileName, "", tempDir)
    val stdin = PipeStream()
    val stdout = PipeStream()
    val stderr = PipeStream()
    val shell = Shell(listOf(stdin, stdout, stderr), inviteSymbol = "", exitOnExceptions = true)

    init {
        tempFile.deleteOnExit()
        for (line in fileContent) {
            tempFile.writeText(line)
        }
    }
}
