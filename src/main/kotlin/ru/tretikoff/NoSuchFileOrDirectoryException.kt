package ru.tretikoff

/**
 * Indicates that the file or directory does not exist
 */
class NoSuchFileOrDirectoryException(filename: String) :
    CliException("$filename: No such file or directory")
