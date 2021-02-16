import streams.ConsoleStream
import streams.FileStream
import streams.Stream
import words.QuotationType
import words.Word
import words.splitByQuotes
import words.splitBySpaces
import java.lang.Exception
import java.util.*
import java.util.logging.Logger

private val consoleStream: Stream = ConsoleStream()

private val logger = Logger.getAnonymousLogger()

private val descriptors = mutableListOf(consoleStream, consoleStream, consoleStream)
var stdin = descriptors[0]
var stdout = descriptors[1]
var stderr = descriptors[2]
var inviteSymb = "$"
private var variables: MutableMap<String, String> = mutableMapOf()

/**
 * The main flow of bash
 */
fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        stdin = FileStream(args[0])
        descriptors[0] = stdin
    }
    while (true) {
        stdout.write(inviteSymb)
        val statement = stdin.read() ?: break
        if (trySetupVariable(statement)) continue
        val statements = mutableListOf<Word>()
        for (x in splitByQuotes(statement)) {
            if (x.quotationType != QuotationType.DoubleQuoted) {
                x.substituteVariables(variables)
            }
            statements.addAll(splitBySpaces(x.value))
        }

        try {
            val pipeIndexes: MutableList<Int> = mutableListOf()
            for (i: Int in 0..statements.size) {
                if (statements[i].isPipe()) {
                    val hasPreviousCommandPipe = if (pipeIndexes.isEmpty()) false else pipeIndexes.last() == i - 1
                    if(i == 0 || hasPreviousCommandPipe) {
                        throw Exception("syntax error near unexpected token `|`")
                    }
                    pipeIndexes.add(i)
                }
            }
            if (pipeIndexes.size == 0) {
                val c = CommandFactory.build(statements, stdin, stdout, stderr)
                c?.execute()
            } else {
                executePipedStatements(statements, pipeIndexes)
            }
        } catch (e: Exception) {
            stderr.writeLine(e.localizedMessage)
        }
    }
}

/**
 * Executes piped commands one by one. Redirects previous command's output to the next command
 */
fun executePipedStatements(statements: List<Word>, pipeIndexes: List<Int>) {
    var fstPipe = PipeStream()
    var lstPipe = PipeStream()

    var curr = 0
    var statement = statements.subList(0, pipeIndexes[curr])
    CommandFactory.build(statement, stdin, fstPipe, stderr)?.execute()
    while (curr < pipeIndexes.size - 1) {
        statement = statements.subList(pipeIndexes[curr], pipeIndexes[curr + 1])
        CommandFactory.build(statement, fstPipe, lstPipe, stderr)?.execute()
        fstPipe = lstPipe
        lstPipe = PipeStream()
        curr++
    }
    statement = statements.subList(0, pipeIndexes[curr])
    lstPipe = fstPipe
    logger.finest("Executing last command: ${statements.joinToString(" ")}")
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
