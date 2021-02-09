package commands

import streams.Stream

class PwdCommand(ins: Stream, out: Stream, err: Stream, args: List<String>, kwargs: Map<String, String>) :
    Command(ins, out, err, args, kwargs) {
    override fun execute(): Int {
        val path = System.getProperty("user.dir")
        outputStream.writeLine(path)
        return 0
    }
}