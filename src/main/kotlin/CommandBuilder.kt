import commands.*
import streams.Stream
import java.lang.Exception

class CommandBuilder {
    companion object {
        fun build(statement: String?, ins: Stream, outs: Stream, errs: Stream): Command {
            val tokens = statement?.split(" ");
            // TODO parse arguments from tokens
            return when (tokens?.get(0)) {
                "exit" -> ExitCommand(ins, outs, errs, tokens.drop(1), mapOf("" to ""))
                "cat" -> CatCommand(ins, outs, errs, tokens.drop(1), mapOf("" to ""))
                "echo" -> EchoCommand(ins, outs, errs, tokens.drop(1), mapOf("" to ""))
                "pwd" -> PwdCommand(ins, outs, errs, tokens.drop(1), mapOf("" to ""))
                "wc" -> WcCommand(ins, outs, errs, tokens.drop(1), mapOf("" to ""))
                else -> throw Exception((tokens?.get(0) ?: "") + ": command not found")
            }
        }
    }
}