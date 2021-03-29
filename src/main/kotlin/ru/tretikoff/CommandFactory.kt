package ru.tretikoff

import ru.tretikoff.commands.CatCommand
import ru.tretikoff.commands.CdCommand
import ru.tretikoff.commands.Command
import ru.tretikoff.commands.EchoCommand
import ru.tretikoff.commands.LsCommand
import ru.tretikoff.commands.ExitCommand
import ru.tretikoff.commands.ExternalCommand
import ru.tretikoff.commands.GrepCommand
import ru.tretikoff.commands.PwdCommand
import ru.tretikoff.commands.WcCommand
import ru.tretikoff.streams.Stream
import ru.tretikoff.words.Word

class CommandFactory private constructor() {
    companion object CommandFactory {
        /**
         * Creates [Command] by the first word token and sends the other tokens as arguments
         * @param words - list of tokens
         * @param ins - input stream for the command
         * @param outs - output stream for the command
         * @param errs - error stream for the command
         * @return created command
         * If no [Command] exists in a custom CLI, [CommandNotFoundException] is thrown
         */
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
                "grep" -> GrepCommand(ins, outs, errs, args)
                "ls" -> LsCommand(ins, outs, errs, args)
                "cd" -> CdCommand(ins, outs, errs, args)
                else -> ExternalCommand(ins, outs, errs, tokens.toMutableList(), tokens.first())
            }
            checkKeys(command, words)

            return command
        }

        private fun checkKeys(command: Command, words: List<Word>) {
            val notFoundKey = words.filter { it.isKey() }.firstOrNull { word ->
                val keys = command.flags.keys
                !(
                    keys.isEmpty() ||
                        keys.map { word.value.startsWith(it) }.reduce { a, x -> a || x }
                    )
            }
            if (notFoundKey != null) {
                throw UnknownOptionException(words[0].value, notFoundKey.value)
            }
        }
    }
}
