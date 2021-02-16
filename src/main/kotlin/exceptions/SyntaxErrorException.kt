package exceptions

/**
 * Indicates the error in the input syntax
 */
class SyntaxErrorException(token: String) :
    Exception("syntax error near unexpected token `$token`")
