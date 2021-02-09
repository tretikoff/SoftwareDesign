package commands

import streams.Stream

class EchoCommand(ins: Stream, out: Stream, err: Stream, args: Map<String, String>) : Command(ins, out, err, args) {

}