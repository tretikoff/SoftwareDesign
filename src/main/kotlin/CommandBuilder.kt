import commands.*
import streams.Stream
import java.lang.Exception

class CommandBuilder {
    companion object {
        fun build(statement: String?, ins: Stream, outs: Stream, errs: Stream): Command {
            val tokens = statement?.split(" ");
            return when (tokens?.get(0)) {
                "exit" -> ExitCommand(ins, outs, errs, tokens.drop(1), mapOf())
                "cat" -> CatCommand(ins, outs, errs, tokens.drop(1), mapOf())
                "echo" -> EchoCommand(ins, outs, errs, tokens.drop(1), mapOf())
                "pwd" -> PwdCommand(ins, outs, errs, tokens.drop(1), mapOf())
                "wc" -> WcCommand(ins, outs, errs, tokens.drop(1), mapOf())
                else -> throw Exception((tokens?.get(0) ?: "") + ": command not found")
            }
        }
    }
}