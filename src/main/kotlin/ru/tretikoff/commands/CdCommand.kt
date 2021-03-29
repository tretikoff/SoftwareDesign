package ru.tretikoff.commands

import ru.tretikoff.streams.FileStream
import ru.tretikoff.streams.Stream
import java.util.logging.Logger
import java.io.File


class CdCommand(
    ins: Stream,
    out: Stream,
    err: Stream,
    args: MutableList<String>,
) :
    Command(ins, out, err, args) {
    private val logger = Logger.getLogger(CatCommand::class.java.name)
    override fun execute(): Int {
        logger.finest("Running cd with arguments $args")
        if (args.isEmpty()) {
            val path = System.getProperty("user.home")
            return cdPath(path)
        } else if (args.count() == 1) {
            return cdPath(args.get(0))
        } else {
            outputStream.writeLine("usage: cd [path]")
            return 1
        }
    }

    private fun cdPath(path: String) : Int {
        val file = File(path).getAbsoluteFile()
        if (!file.exists()) {
            errorStream.writeLine("no such directory: \"$path\"")
            return 2
        } else if (!file.isDirectory()) {
            errorStream.writeLine("not a directory: \"$path\"")
            return 3
        }
        System.setProperty("user.dir", file.getAbsolutePath())
        return 0
    }
}
