package ru.tretikoff

import kotlin.test.Test
import kotlin.test.assertEquals

class LsTest : BashTest() {
    @Test
    fun lsDir() {
        stdin.writeLine("ls src")
        shell.run()
        assertEquals("main", stdout.read())
        assertEquals("test", stdout.read())
    }

    @Test
    fun lsFile() {
        stdin.writeLine("ls gradlew.bat")
        shell.run()
        assertEquals("gradlew.bat", stdout.read())
    }

    @Test
    fun lsErr() {
        stdin.writeLine("ls abaaa")
        shell.run()
        assertEquals("no such file or directory: \"abaaa\"", stderr.read())
    }
}
