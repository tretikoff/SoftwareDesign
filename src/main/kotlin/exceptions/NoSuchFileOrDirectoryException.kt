package exceptions

/**
 * Indicates that the file or directory does not exist
 */
class NoSuchFileOrDirectoryException(cmd: String, filename: String) :
    Exception("$cmd: $filename: No such file or directory")
