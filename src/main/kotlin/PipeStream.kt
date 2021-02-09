import streams.Stream
import java.util.*

class PipeStream : Stream() {
    var buffer: Queue<String> = LinkedList()
    override fun read(): String? {
        if (buffer.isNotEmpty()) {
            return buffer.poll()
        }
        return null
    }

    override fun write(statement: String?) {
        if (statement == null) return
        val tokens = statement.split("\n")
        if (tokens.isEmpty()) return
        if (buffer.isNotEmpty()) {
            var last = buffer.poll()
            if (!last.endsWith('\n')) {
                last += tokens[0]
                tokens.drop(1)
            }
            buffer.add(last)
        }
        for (token in tokens) {
            buffer.add(token)
        }
    }
}