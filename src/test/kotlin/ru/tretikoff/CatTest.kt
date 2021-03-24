package ru.tretikoff

import kotlin.test.Test
import kotlin.test.assertEquals

class CatTest : BashTest() {
    @Test
    fun catFileInput() {
        stdin.writeLine("cat test")
        shell.run()
        for (line in fileContent) {
            assertEquals(line, stdout.read())
        }
    }
}
