package commands

import streams.Stream

open class Command(ins: Stream, out: Stream, err: Stream, args: Map<String, String>) {
    val input: Stream = ins
    val output: Stream = out
    val error: Stream = err
    val arguments: Map<String, String> = args

    open fun execute(): Int {
        return 0
    }
}