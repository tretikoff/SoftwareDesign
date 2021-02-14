package commands

import streams.Stream

class EchoCommand(
    ins: Stream,
    out: Stream,
    err: Stream,
    args: List<String>,
    kwargs: Map<String, String>
) :
    Command(ins, out, err, args, kwargs) {
    override fun execute(): Int {
        outputStream.writeLine(args.joinToString(separator = " "))
        return 0
    }
}
