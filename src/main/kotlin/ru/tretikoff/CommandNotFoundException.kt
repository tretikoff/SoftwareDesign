package ru.tretikoff

/**
 * Indicates that command entered by user was not found
 */
class CommandNotFoundException(val cmd: String) :
    CliException("$cmd: command not found")
