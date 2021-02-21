package ru.tretikoff.commands

import ru.tretikoff.streams.Stream
import kotlin.system.exitProcess

/**
 * The exit utility shall cause the shell to exit with the exit status specified by the unsigned
 * decimal integer n. If n is specified, but its value is not between 0 and 255 inclusively,
 * the exit status is undefined.
 * A trap on EXIT shall be executed before the shell terminates, except when the exit utility
 * is invoked in that trap itself, in which case the shell shall exit immediately.
 * The exit status shall be n, if specified. Otherwise, the value shall be the exit value of
 * the last command executed, or zero if no command was executed. When exit is executed in
 * a trap action, the last command is considered to be the command that executed
 * immediately preceding the trap action.
 */
class ExitCommand(
    ins: Stream,
    out: Stream,
    err: Stream,
    args: MutableList<String>,
) : Command(ins, out, err, args) {
    override fun execute(): Int {
        exitProcess(
            if (args.isNotEmpty()) {
                args[0].toInt()
            } else 0
        )
    }
}
