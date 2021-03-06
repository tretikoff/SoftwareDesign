package ru.tretikoff.commands

import ru.tretikoff.streams.Stream

/**
 * The pwd utility shall write to standard output an absolute pathname
 * of the current working directory, which does not contain the filenames dot or dot-dot.
 */
class PwdCommand(
    ins: Stream,
    out: Stream,
    err: Stream,
    args: MutableList<String>,
) :
    Command(ins, out, err, args) {
    override fun execute(): Int {
        val path = System.getProperty("user.dir")
        outputStream.writeLine(path)
        return 0
    }
}
