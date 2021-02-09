import streams.ConsoleStream
import streams.Stream
import java.lang.Exception

private val consoleStream = ConsoleStream()
//0 stdin, 1 stdout, 2 stderr
private val descriptors: Map<Int, Stream> =
    mapOf(0 to consoleStream, 1 to consoleStream, 2 to consoleStream)

fun main(args: Array<String>) {
    while (true) {
        val s = descriptors[0]!!.read()!!
        try {
            val c = CommandBuilder.build(s, descriptors[0]!!, descriptors[1]!!, descriptors[2]!!)
            c.execute()
            //TODO make own exception
        } catch (e: Exception) {
            descriptors[2]!!.write(e.localizedMessage)
        }
    }}