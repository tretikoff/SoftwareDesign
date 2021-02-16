package commands

import exceptions.NoSuchFileOrDirectoryException
import streams.FileStream
import streams.Stream
import java.io.FileNotFoundException

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
    args: List<String>,
    kwargs: Map<String, String>
) :
    Command(ins, out, err, args, kwargs) {
    override fun execute(): Int {
        for (filename in args) {
            try {
                val stream = FileStream(filename)
                printLCW(stream)
            } catch (e: FileNotFoundException) {
                throw NoSuchFileOrDirectoryException("wc", filename)
            }
        }
        if (args.isEmpty()) {
            printLCW(inputStream)
        }
        return 0
    }

    private fun printLCW(stream: Stream) {
        var l = 0
        var w = 0
        var c = 0
        while (true) {
            val b = stream.read()
            if (b == null) {
                outputStream.writeLine("$l\t$w\t$c")
                return
            }
            c += b.length
            w += b.split(" \n\t").size
            l += 1
        }
    }
}
