package commands

import streams.Stream
import kotlin.system.exitProcess

class ExitCommand(
    ins: Stream,
    out: Stream,
    err: Stream,
    args: List<String>,
    kwargs: Map<String, String>
) : Command(ins, out, err, args, kwargs) {
    override fun execute(): Int {
        exitProcess(
            if (args.isNotEmpty()) {
                args[0].toInt()
            } else 0
        )
    }
}
