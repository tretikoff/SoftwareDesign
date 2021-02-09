import commands.*
import streams.Stream
import java.lang.Exception

class CommandBuilder {
    companion object {
        fun build(statement: String, ins: Stream, outs: Stream, errs: Stream): Command {
            val tokens = statement.split(" ");
            return when (tokens[0]) {
                "exit" -> ExitCommand(ins, outs, errs, mapOf())
                "cat" -> CatCommand(ins, outs, errs, mapOf())
                "echo" -> EchoCommand(ins, outs, errs, mapOf())
                "pwd" -> PwdCommand(ins, outs, errs, mapOf())
                "wc" -> WcCommand(ins, outs, errs, mapOf())
                else -> throw Exception(tokens[0] + ": command not found")
            }
        }
    }
}