package ru.tretikoff.commands

import ru.tretikoff.streams.FileStream
import ru.tretikoff.streams.Stream
import java.util.logging.Logger

/**
 * The utility displays the number of lines, words, and bytes contained in each input file
 * or standard input (if no file is specified) to the standard output.
 * A line is defined as a string of characters delimited by a Aq newline character.
 * Characters beyond the final Aq newline character will not be included in the line count.
 * A word is defined as a string of characters delimited by white space characters.
 * White space characters are the set of characters for which the iswspace(3) function returns true.
 * If more than one input file is specified, a line of cumulative counts for all the files
 * is displayed on a separate line after the output for the last file.
 */
class WcCommand(
    ins: Stream,
    out: Stream,
    err: Stream,
    args: MutableList<String>,
) :
    Command(ins, out, err, args) {
    private val logger = Logger.getLogger(WcCommand::class.java.name)
    override fun execute(): Int {
        for (filename in args) {
            val stream = FileStream(filename)
            printLCW(stream, filename)
        }
        if (args.isEmpty()) {
            printLCW(inputStream)
        }
        return 0
    }

    private fun printLCW(stream: Stream, filename: String? = null) {
        var l = 0
        var w = 0
        var c = 0
        while (true) {
            val b = stream.read()
            logger.finest("wc: processing line $b")
            if (b == null) {
                outputStream.writeLine(
                    "$l\t$w\t$c" +
                        if (!filename.isNullOrEmpty()) "\t$filename" else ""
                )
                return
            }
            c += b.length
            val words = b.split(" ", "\t").filter { x -> x.isNotEmpty() }
            w += words.size
            logger.finest("wc: processing words: ${words.joinToString(", ")}, ${words.size}")
            l += if (b.isNotEmpty()) 1 else 0
        }
    }
}
