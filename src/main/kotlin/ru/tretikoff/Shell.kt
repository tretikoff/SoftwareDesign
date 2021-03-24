package ru.tretikoff

import ru.tretikoff.streams.Stream
import ru.tretikoff.words.QuotationType
import ru.tretikoff.words.Word
import java.util.logging.Logger

/**
 * Shell instance which reads commands from stdin descriptor and executes them one by one
 * @param descriptors - list of descriptors to work with
 * @param inviteSymbol - invite symbol for user to see before command executing
 * @param exitOnExceptions - indicates whether shell should quit main loop on command execution error
 */
@Suppress("SwallowedException")
class Shell(
    descriptors: List<Stream>,
    var inviteSymbol: String = "$",
    var exitOnExceptions: Boolean = false
) {
    private val logger = Logger.getLogger(Shell::class.java.name)
    private var variables: MutableMap<String, String> = mutableMapOf()
    private val parser = Parser()
    var stdin = descriptors[0]
    var stdout = descriptors[1]
    var stderr = descriptors[2]
    fun run() {
        while (true) {
            stdout.write(inviteSymbol)
            val statement = stdin.read() ?: return
            logger.finest(statement)
            if (trySetupVariable(statement)) continue
            val words = parser.parse(statement)

            logger.finest(words.joinToString(", ") { it.value })
            words.filter { it.quotationType != QuotationType.SingleQuoted }
                .forEach { it.substituteVariables(variables) }

            try {
                val pipeIndexes = extractPipes(words)
                if (pipeIndexes.isEmpty()) {
                    val c = CommandFactory.build(words, stdin, stdout, stderr)
                    c?.execute()
                } else {
                    executePipedStatements(words, pipeIndexes)
                }
            } catch (e: CliException) {
                handleException(e)
            }
        }
    }

    private fun handleException(e: CliException) {
        logger.severe(e.localizedMessage)
        stderr.writeLine(e.localizedMessage)
        if (exitOnExceptions) throw e
    }

    private fun extractPipes(words: List<Word>): List<Int> {
        val pipeIndexes: MutableList<Int> = mutableListOf()
        for (i: Int in words.indices) {
            if (words[i].isPipe()) {
                val hasPreviousCommandPipe =
                    pipeIndexes.isNotEmpty() && pipeIndexes.last() == i - 1
                if (i == 0 || hasPreviousCommandPipe) {
                    throw SyntaxErrorException("`|`")
                }
                pipeIndexes.add(i)
            }
        }
        if (!pipeIndexes.isEmpty() && pipeIndexes.last() == words.size - 1) {
            throw SyntaxErrorException("`|`")
        }
        return pipeIndexes
    }

    /**
     * Executes piped commands one by one. Redirects previous command's output to the next command
     * @param statements - list of tokens to execute
     * @param pipeIndexes - indexes of pipes in tokens
     */
    private fun executePipedStatements(statements: List<Word>, pipeIndexes: List<Int>) {
        var fstPipe = PipeStream()
        var lstPipe = PipeStream()

        var statement = statements.subList(0, pipeIndexes[0])
        logger.finest(
            "Executing piped statements: ${statements.joinToString(" ") { x -> x.value }}" +
                " pipes on the ${pipeIndexes.joinToString(", ")}"
        )
        logger.finest("Executing first command: ${statement.joinToString(" ") { x -> x.value }}")
        CommandFactory.build(statement, stdin, fstPipe, stderr)?.execute()
        for ((curr, prev) in pipeIndexes.zipWithNext()) {
            statement = statements.subList(curr + 1, prev)
            logger.finest("Executing command: ${statement.joinToString(" ") { x -> x.value }}")
            CommandFactory.build(statement, fstPipe, lstPipe, stderr)?.execute()
            fstPipe = lstPipe
            lstPipe = PipeStream()
        }
        statement = statements.subList(pipeIndexes.last() + 1, statements.size)
        lstPipe = fstPipe
        logger.finest("Executing last command: ${statement.joinToString(" ") { x -> x.value }}")
        CommandFactory.build(statement, lstPipe, stdout, stderr)?.execute()
    }

    private fun trySetupVariable(statement: String): Boolean {
        val tokens = statement.split("=")
        if (tokens.size != 2) return false
        val name = tokens[0]
        val value = tokens[1]
        logger.finest("setting variable $name with value $value in $statement")
        variables[name] = value
        return true
    }
}
