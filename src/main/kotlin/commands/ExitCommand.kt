package commands

import streams.Stream
import kotlin.system.exitProcess

class ExitCommand(ins: Stream, out: Stream, err: Stream, args: Map<String, String>) : Command(ins, out, err, args) {
    override fun execute(): Int {
        ///TODO how to store unnamed args
        exitProcess(arguments["status"]?.toInt() ?: 0)
    }
}