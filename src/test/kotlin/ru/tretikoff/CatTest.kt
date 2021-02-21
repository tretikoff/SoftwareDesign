package ru.tretikoff

import kotlin.test.Test
import kotlin.test.assertEquals

class CatTest : BashTest() {
    @Test
    fun catFileInput() {
        stdin.writeLine("cat test")
        main()
        for (line in fileContent) {
            assertEquals(line, stdout.read())
        }
    }
}
