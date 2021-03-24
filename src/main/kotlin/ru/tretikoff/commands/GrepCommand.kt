package ru.tretikoff.commands

import ru.tretikoff.streams.FileStream
import ru.tretikoff.streams.Stream
import java.util.logging.Logger

/**
 * grep searches the named input FILEs (or standard input if no files are named,
 * or the file name - is given) for lines containing a match to the given PATTERN.
 * By default, grep prints the matching lines.
 *
 * Keys:
 * -i
 * Ignore case distinctions in both the PATTERN and the input files.
 *
 * -w
 * Select only those lines containing matches that form whole words.
 * The test is that the matching substring must either be at the beginning of the line,
 * or preceded by a non-word constituent character. Similarly,
 * it must be either at the end of the line or followed by a non-word constituent character.
 * Word-constituent characters are letters, digits, and the underscore.
 *
 * -A NUM
 * Print NUM lines of trailing context after matching lines.
 * Places a line containing -- between contiguous groups of matches.
 */
@Suppress("UnusedPrivateMember", "NestedBlockDepth")
class GrepCommand(
    ins: Stream,
    out: Stream,
    err: Stream,
    args: MutableList<String>,
) :
    Command(ins, out, err, args) {
    init {
        flags = mutableMapOf("-i" to false, "-A" to false, "-w" to false)
    }

    var printAfter = 0

    private val logger = Logger.getLogger(GrepCommand::class.java.name)
    override fun execute(): Int {
        logger.finest("Running grep with arguments $args")
        for (arg in args) {
            if (arg.startsWith("-A")) {
                try {
                    printAfter = arg.substring(2).toInt()
                } catch (e: NumberFormatException) {
                    val linesCountIdx = args.indexOf(arg) + 1
                    printAfter = args[linesCountIdx].toInt()
                }
            }
        }
        for (arg in listOf("-i", "-A", "-w")) {
            if (args.contains(arg)) {
                flags[arg] = true
                if (arg == "-A") {
                    args.removeAt(args.indexOf(arg) + 1)
                }
                args.remove(arg)
            }
        }
        if (args.size < 1) {
            outputStream.writeLine("Usage: grep [OPTION]... PATTERNS [FILE]")
            return 1
        }
        val search = args[0]
        val stream: Stream = if (args.size > 1) FileStream(args[1]) else inputStream
        runPatternSearch(stream, search)
        return 0
    }

    private fun runPatternSearch(stream: Stream, search: String) {
        var reg = search.toRegex()
        if (flags["-w"]!!) {
            reg = "\\s${reg.pattern}\\s".toRegex()
        }
        var v = stream.read()
        while (v != null) {
            if (v.contains(reg) ||
                (flags["-i"]!! && v.toLowerCase().contains(search.toLowerCase()))
            ) {
                outputStream.writeLine(v)
                for (i in 0 until printAfter) {
                    v = stream.read()
                    if (v == null) return
                    outputStream.writeLine(v)
                }
            }
            v = stream.read()
        }
    }
}
