package exceptions

/**
 * Indicates the flag given is not recognized
 */
class UnknownOptionException(cmd: String, option: String) :
    Exception("$cmd: unrecognized option '$option'")
