package exceptions

class NoSuchFileOrDirectoryException(cmd: String, filename: String) :
    Exception("${cmd}: ${filename}: No such file or directory") {
}