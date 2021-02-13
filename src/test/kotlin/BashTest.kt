import java.io.File


open class BashTest {
    val fileContent = listOf("first line", "second line", "", "fourth line")
    val tempFile = File.createTempFile("test", "")

    init {

        stdin = PipeStream()
        stdout = PipeStream()
        stderr = PipeStream()
        tempFile.deleteOnExit()
        for (line in fileContent) {
            tempFile.writeText(line)
        }
        val txt = tempFile.readText()
        println("reading $txt")
    }
}