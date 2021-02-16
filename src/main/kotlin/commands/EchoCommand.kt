package commands

import streams.Stream

/**
 * The echo utility writes its arguments to standard output, followed by a <newline>.
 * If there are no arguments, only the <newline> is written.
 */
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
