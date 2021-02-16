package exceptions

/**
 * Indicates that command entered by user was not found
 */
class CommandNotFoundException(cmd: String) :
    Exception("$cmd: command not found")
