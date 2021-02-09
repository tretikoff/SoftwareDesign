import streams.ConsoleStream
import streams.Stream
import java.lang.Exception
import java.util.*

private val consoleStream = ConsoleStream()

//0 stdin, 1 stdout, 2 stderr
private val descriptors: List<Stream> = listOf(consoleStream, consoleStream, consoleStream)

fun main(args: Array<String>) {
    //TODO добавить переменные
    //TODO добавить тесты
    while (true) {
        descriptors[1].write("$")
        val statements = descriptors[0].read()!!
        try {
            val statementsList: Queue<String> = LinkedList(statements.split("|"))
            if (statementsList.size == 1) {
                val c = CommandBuilder.build(statements, descriptors[0], descriptors[1], descriptors[2])
                c.execute()
            } else {
                var fstPipe = PipeStream()
                var lstPipe = PipeStream()
                CommandBuilder.build(statementsList.poll(), descriptors[0], fstPipe, descriptors[2]).execute()
                while (statementsList.size > 1) {
                    CommandBuilder.build(statementsList.poll(), fstPipe, lstPipe, descriptors[2]).execute()
                    fstPipe = lstPipe
                    lstPipe = PipeStream()
                }
                CommandBuilder.build(statementsList.poll(), lstPipe, descriptors[1], descriptors[2])
            }
        } catch (e: Exception) {
            descriptors[2].write(e.localizedMessage)
        }
    }
}