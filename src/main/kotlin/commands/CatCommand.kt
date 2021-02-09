package commands

import exceptions.NoSuchFileOrDirectoryException
import streams.FileStream
import streams.Stream
import java.io.FileNotFoundException

class CatCommand(ins: Stream, out: Stream, err: Stream, args: List<String>, kwargs: Map<String, String>) :
    Command(ins, out, err, args, kwargs) {
    override fun execute(): Int {
        if (args.isNotEmpty()) {
            for (filename in args) {
                try {
                    val stream = FileStream(filename)
                    printToOutput(stream)
                } catch (e: FileNotFoundException) {
                    throw NoSuchFileOrDirectoryException("cat", filename)
                }
            }
        } else {
            printToOutput(inputStream)
        }
        return 0
    }

    private fun printToOutput(stream: Stream) {
        val v = stream.read() ?: return
        outputStream.writeLine(v)
    }
}