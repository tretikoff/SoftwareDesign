import commands.*
import streams.Stream
import words.Word
import java.lang.Exception

class CommandFactory {
    companion object {
        fun build(words: List<Word>, ins: Stream, outs: Stream, errs: Stream): Command? {
            val tokens = words.map { x -> x.value }
            if (tokens.isEmpty()) return null
            return when (tokens[0]) {
                "exit" -> ExitCommand(ins, outs, errs, tokens.drop(1), mapOf())
                "cat" -> CatCommand(ins, outs, errs, tokens.drop(1), mapOf())
                "echo" -> EchoCommand(ins, outs, errs, tokens.drop(1), mapOf())
                "pwd" -> PwdCommand(ins, outs, errs, tokens.drop(1), mapOf())
                "wc" -> WcCommand(ins, outs, errs, tokens.drop(1), mapOf())
                else -> throw Exception((tokens[0]) + ": command not found")
            }
        }
    }
}
