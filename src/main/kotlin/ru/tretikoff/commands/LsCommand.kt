package ru.tretikoff.commands

import ru.tretikoff.streams.Stream
import java.io.File
import java.util.logging.Logger

class LsCommand(
    ins: Stream,
    out: Stream,
    err: Stream,
    args: MutableList<String>,
) :
    Command(ins, out, err, args) {
    private val logger = Logger.getLogger(CatCommand::class.java.name)
    override fun execute(): Int {
        logger.finest("Running ls with arguments $args")
        if (args.isEmpty()) {
            val path = System.getProperty("user.dir")
            return lsPath(path)
        } else if (args.count() == 1) {
            return lsPath(args.get(0))
        } else {
            outputStream.writeLine("usage: ls [path]")
            return 1
        }
    }

    private fun lsPath(path: String): Int {
        var parent = ""
        if (!path.startsWith("/")) {
            parent = System.getProperty("user.dir")
        }
        val file = File(parent, path).getAbsoluteFile()
        val files = file.list()
        if (!file.exists()) {
            errorStream.writeLine("no such file or directory: \"$path\"")
            return 2
        }
        if (files == null) {
            outputStream.writeLine(path)
        } else {
            files.forEach {
                if (!it.startsWith(".")) {
                    outputStream.writeLine(it)
                }
            }
        }
        return 0
    }
}
