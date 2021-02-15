import streams.ConsoleStream
import streams.FileStream
import streams.Stream
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

fun main(args: Array<String>) {
    if (args.isNotEmpty()) {
        stdin = FileStream(args[0])
        descriptors[0] = stdin
    }
    while (true) {
        stdout.write(inviteSymb)
        var statements = stdin.read()
        if (statements == null) break
        if (trySetupVariable(statements)) continue
        statements = substituteVariables(statements)

        try {
            val statementsList: Queue<String> = LinkedList(statements.split("|"))
            logger.finest("Statements was read from the terminal: $statementsList")
            if (statementsList.size == 1) {
                val c = CommandBuilder.build(statements, stdin, stdout, stderr)
                c.execute()
            } else {
                executePipedStatements(statementsList)
            }
        } catch (e: Exception) {
            descriptors[2].writeLine(e.localizedMessage)
        }
    }
}

fun executePipedStatements(statementsList: Queue<String>) {
    var fstPipe = PipeStream()
    var lstPipe = PipeStream()

    CommandBuilder.build(statementsList.poll().trim(), stdin, fstPipe, stderr).execute()
    while (statementsList.size > 1) {
        CommandBuilder.build(statementsList.poll().trim(), fstPipe, lstPipe, stderr).execute()
        fstPipe = lstPipe
        lstPipe = PipeStream()
    }
    logger.finest("Executing last command: ${statementsList.peek().trim()}")
    lstPipe = fstPipe
    CommandBuilder.build(statementsList.poll().trim(), lstPipe, stdout, stderr).execute()
}

fun trySetupVariable(statement: String): Boolean {
    val tokens = statement.split("=")
    if (tokens.size != 2) return false
    val name = tokens[0]
    val value = tokens[1]
    logger.finest("setting variable $name with value $value in $statement")
    variables[name] = value
    return true
}

fun substituteVariables(statement: String): String {
    var replaced = statement
    while (true) {
        val regex = "\\$\\w+".toRegex()
        val match = regex.find(replaced) ?: return replaced
        val replacement = variables[match.value.drop(1)] ?: ""
        logger.finest("replacing ${match.value} with $replacement in $statement")
        replaced = replaced.replace(regex, replacement)
    }
}
