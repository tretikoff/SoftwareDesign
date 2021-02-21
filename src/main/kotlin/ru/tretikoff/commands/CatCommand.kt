package ru.tretikoff.commands

import ru.tretikoff.streams.FileStream
import ru.tretikoff.streams.Stream
import java.util.logging.Logger

/**
 * The cat utility shall read files in sequence and shall write their contents
 * to the standard output in the same sequence.
 */
class CatCommand(
    ins: Stream,
    out: Stream,
    err: Stream,
    args: MutableList<String>,
) :
    Command(ins, out, err, args) {
    private val logger = Logger.getLogger(CatCommand::class.java.name)
    override fun execute(): Int {
        logger.finest("Running cat with arguments $args")
        if (args.isNotEmpty()) {
            for (filename in args) {
                val stream = FileStream(filename)
                printToOutput(stream)
            }
        } else {
            printToOutput(inputStream)
        }
        return 0
    }

    private fun printToOutput(stream: Stream) {
        var v = stream.read()
        while (v != null) {
            logger.finest("cat writing $v")
            outputStream.writeLine(v)
            v = stream.read()
        }
    }
}
