package ru.tretikoff.commands

import ru.tretikoff.CommandNotFoundException
import ru.tretikoff.streams.Stream
import java.io.IOException
import java.io.BufferedReader
import java.io.InputStreamReader
import java.util.logging.Logger


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
    private val logger = Logger.getLogger(ExternalCommand::class.java.name)

    override fun execute(): Int {
        try {
            val p = Runtime.getRuntime().exec(args[0])
            p.waitFor()
            val stdInput = BufferedReader(InputStreamReader(p.inputStream))
            val stdError = BufferedReader(InputStreamReader(p.errorStream))
            var s: String?
            while (stdInput.readLine().also { s = it } != null) {
                outputStream.writeLine(s)
            }
            while (stdError.readLine().also { s = it } != null) {
                errorStream.writeLine(s)
            }
            return p.exitValue()
        } catch (e: IOException) {
            logger.info("Executing external command $name failed")
            logger.info(args[0])
            throw CommandNotFoundException(name)
        }
    }
}
