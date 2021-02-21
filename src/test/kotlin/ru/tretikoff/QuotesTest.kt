package ru.tretikoff

import kotlin.test.Test
import kotlin.test.assertEquals

class QuotesTest : BashTest() {
    @Test
    fun `single quote string`() {
        val str = "hello"
        stdin.writeLine("echo '$str'")
        main()
        assertEquals(str, stdout.read())
    }

    @Test
    fun `double quote string`() {
        val str = "hello"
        stdin.writeLine("echo \"$str\"")
        main()
        assertEquals(str, stdout.read())
    }

    @Test
    fun `double quote string substitutes variable`() {
        val str = "hello"
        stdin.writeLine("str=$str")
        stdin.writeLine("echo \"\$str\"")
        main()
        assertEquals(str, stdout.read())
    }
    @Test
    fun `single quote command do not substitute variable`() {
        val str = "hello"
        stdin.writeLine("str=$str")
        stdin.writeLine("echo \"\$str\"")
        main()
        assertEquals(str, stdout.read())
    }
}
