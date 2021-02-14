import streams.Stream
import java.util.*
import java.util.logging.Logger

class PipeStream : Stream() {
    private var buffer: ArrayDeque<String> = ArrayDeque()
    private val logger = Logger.getLogger(PipeStream::class.java.name)
    override fun read(): String? {
        var read: String? = null
        if (buffer.isNotEmpty()) {
            read = buffer.poll()
        }
        logger.finest("reading $read from $buffer")
        return read
    }

    override fun write(statement: String?) {
        logger.finest("writing $statement")
        if (statement == null) return
        var tokens = statement.split("\n")
        if (tokens.isEmpty()) tokens = listOf(statement)
        if (buffer.isNotEmpty()) {
            var last = buffer.pollLast()
            if (!last.endsWith('\n')) {
                last += tokens[0]
                tokens = tokens.drop(1)
            }
            buffer.add(last)
        }
        for (token in tokens) {
            buffer.add(token)
        }
    }
}
