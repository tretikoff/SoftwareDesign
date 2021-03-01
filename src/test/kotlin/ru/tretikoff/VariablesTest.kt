package ru.tretikoff

import kotlin.test.Test
import kotlin.test.assertEquals

class VariablesTest : BashTest() {

    @Test
    fun `set variable`() {
        stdin.writeLine("var=test")
        stdin.writeLine("cat \$var")
        shell.run()
        for (line in fileContent) {
            assertEquals(line, stdout.read())
        }
    }
}
