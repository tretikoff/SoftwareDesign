package ru.tretikoff

import kotlin.test.Test
import kotlin.test.assertEquals

class PipeTest : BashTest() {
    @Test
    fun `two commands pipe`() {
        stdin.writeLine("cat test | cat")
        shell.run()
        for (line in fileContent) {
            assertEquals(line, stdout.read())
        }
    }

    @Test
    fun `pipe to wc`() {
        stdin.writeLine("cat test | wc")
        shell.run()
        assertEquals("3\t6\t32", stdout.read())
    }

    @Test
    fun `double pipe to wc`() {
        stdin.writeLine("cat test | wc | wc")
        shell.run()
        assertEquals("1\t3\t6", stdout.read())
    }
}
