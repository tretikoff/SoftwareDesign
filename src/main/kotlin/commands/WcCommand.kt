package commands

import exceptions.NoSuchFileOrDirectoryException
import streams.FileStream
import streams.Stream
import java.io.FileNotFoundException

class WcCommand(ins: Stream, out: Stream, err: Stream, args: List<String>, kwargs: Map<String, String>) :
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