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

fun main(args: Array<String>) {
    //TODO добавить переменные
    //TODO добавить тесты
    //TODO добавить кавычки и скобочки
    while (true) {
        stdout.write("$")
        val statements = stdin.read()!!
        try {
            val statementsList: Queue<String> = LinkedList(statements.split("|"))
            logger.finest("Statements was read from the terminal: $statementsList")
            if (statementsList.size == 1) {
                val c = CommandBuilder.build(statements, stdin, stdout, stderr)
                c.execute()
            } else {
                //TODO move to some class or function
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
        } catch (e: Exception) {
            descriptors[2].writeLine(e.localizedMessage)
        }
    }
}