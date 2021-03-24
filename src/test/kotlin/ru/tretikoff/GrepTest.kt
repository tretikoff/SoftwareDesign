package ru.tretikoff

import kotlin.test.Test
import kotlin.test.assertEquals

class GrepTest : BashTest() {
    @Test
    fun testFindPattern() {
        stdin.writeLine("grep f.*line test")
        shell.run()
        assertEquals(fileContent[0], stdout.read())
        assertEquals(fileContent[3], stdout.read())
    }

    @Test
    fun testFindSubstring() {
        stdin.writeLine("grep cond test")
        shell.run()
        assertEquals(fileContent[1], stdout.read())
    }

    @Test
    fun testIgnoreCase() {
        val search = "eCOnD"
        stdin.writeLine("grep -i $search test")
        shell.run()
        assertEquals(fileContent[1], stdout.read())
    }

    @Test
    fun testShowLinesAfter() {
        val search = "econd"
        stdin.writeLine("grep $search test -A1")
        shell.run()
        assertEquals(fileContent[1], stdout.read())
        assertEquals(fileContent[2], stdout.read())
    }

    @Test
    fun testShowLinesAfterWithSpace() {
        val search = "econd"
        stdin.writeLine("grep -A 1 $search test")
        shell.run()
        assertEquals(fileContent[1], stdout.read())
        assertEquals(fileContent[2], stdout.read())
    }

    @Test
    fun testSearchesWord() {
        val line = fileContent[0]
        stdin.writeLine("grep ${line.split(" ").first()} test")
        shell.run()
        assertEquals(line, stdout.read())
    }
}
