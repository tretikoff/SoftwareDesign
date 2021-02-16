import commands.*
import exceptions.CommandNotFoundException
import exceptions.UnknownOptionException
import streams.Stream
import words.Word

/**
 * Creates command by the first word token and sends the other tokens as arguments
 */
class CommandFactory {
    companion object {
        fun build(words: List<Word>, ins: Stream, outs: Stream, errs: Stream): Command? {
            val tokens = words.map { x -> x.value }
            if (tokens.isEmpty()) return null
            val args = tokens.drop(1).toMutableList()
            val command = when (tokens[0]) {
                "exit" -> ExitCommand(ins, outs, errs, args)
                "cat" -> CatCommand(ins, outs, errs, args)
                "echo" -> EchoCommand(ins, outs, errs, args)
                "pwd" -> PwdCommand(ins, outs, errs, args)
                "wc" -> WcCommand(ins, outs, errs, args)
                else -> throw CommandNotFoundException(tokens[0])
            }
            for (word in words) {
                if (word.isKey()) {
                    fun findKey(): Boolean {
                        for (x in command.flags.keys) {
                            if (word.value.startsWith(x)) {
                                return true
                            }
                        }
                        return false
                    }
                    if (!findKey()) {
                        throw UnknownOptionException(tokens[0], word.value)
                    }
                }
            }
            return command
        }
    }
}
