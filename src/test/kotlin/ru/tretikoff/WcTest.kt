package ru.tretikoff

import kotlin.test.Test
import kotlin.test.assertEquals

class WcTest : BashTest() {
    @Test
    fun `wc file test`() {
        stdin.writeLine("wc test")
        shell.run()
        assertEquals("3\t6\t32\ttest", stdout.read())
    }
}
