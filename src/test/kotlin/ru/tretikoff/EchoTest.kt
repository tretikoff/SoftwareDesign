package ru.tretikoff

import kotlin.test.Test
import kotlin.test.assertEquals

class EchoTest : BashTest() {
    @Test
    fun `echo string`() {
        val str = "hello"
        stdin.writeLine("echo $str")
        shell.run()
        assertEquals(str, stdout.read())
    }

    @Test
    fun `echo empty string`() {
        stdin.writeLine("echo")
        shell.run()
        assert(stdout.read().isNullOrEmpty())
    }
}
