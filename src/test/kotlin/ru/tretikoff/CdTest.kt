package ru.tretikoff

import kotlin.test.Test
import kotlin.test.assertEquals

class CdTest : BashTest() {
    @Test
    fun cdHome() {
        stdin.writeLine("cd")
        stdin.writeLine("pwd")
        shell.run()
        assertEquals(System.getProperty("user.home"), stdout.read())
    }

    @Test
    fun cdDir() {
        stdin.writeLine("cd src")
        stdin.writeLine("ls")
        shell.run()
        assertEquals("main", stdout.read())
        assertEquals("test", stdout.read())
    }

    @Test
    fun cdErr() {
        stdin.writeLine("cd abaaa")
        shell.run()
        assertEquals("no such directory: \"abaaa\"", stderr.read())
    }
}
