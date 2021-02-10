import streams.ConsoleStream
import streams.Stream
import java.lang.Exception
import java.util.*
import java.util.logging.Logger

private val consoleStream = ConsoleStream()

private val logger = Logger.getAnonymousLogger()

private val descriptors: List<Stream> = listOf(consoleStream, consoleStream, consoleStream)
private val stdin = descriptors[0]
private val stdout = descriptors[1]
private val stderr = descriptors[2]
private var variables: MutableMap<String, String> = mutableMapOf()

fun main(args: Array<String>) {
    while (true) {
        stdout.write("$")
        var statements = stdin.read()!!
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
    variables[tokens[0]] = tokens[1]
    return true
}

fun substituteVariables(statement: String): String {
    var replaced = statement
    while (replaced.contains("$")) {
        val regex = "\$\\w+".toRegex()
        val match = regex.find(replaced)!!
        replaced = replaced.replace(regex, variables[match.value.drop(1)] ?: "")
    }
    return replaced
}