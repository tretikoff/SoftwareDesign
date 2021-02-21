package ru.tretikoff

/**
 * Indicates the error in the input syntax
 */
class SyntaxErrorException(token: String) :
    CliException("syntax error near unexpected token `$token`")
