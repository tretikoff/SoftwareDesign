package ru.tretikoff.commands

import ru.tretikoff.CommandNotFoundException
import ru.tretikoff.streams.Stream
import java.io.IOException

/**
 * An entity to execute the commands, which could not be executed in the CLI
 * @param name - name of the command executed
 */
@Suppress("SwallowedException")
class ExternalCommand(
    ins: Stream,
    out: Stream,
    err: Stream,
    args: MutableList<String>,
    private val name: String,
) : Command(ins, out, err, args) {
    override fun execute(): Int {
        try {
            val p = Runtime.getRuntime().exec(args[0])
            p.waitFor()
            return p.exitValue()
        } catch (e: IOException) {
            throw CommandNotFoundException(name)
        }
    }
}
