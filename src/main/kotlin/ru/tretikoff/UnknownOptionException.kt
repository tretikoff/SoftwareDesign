package ru.tretikoff

/**
 * Indicates the flag given is not recognized
 */
class UnknownOptionException(cmd: String, option: String) :
    CliException("$cmd: unrecognized option '$option'")
