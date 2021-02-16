import org.junit.Test

class PipeTest : BashTest() {
    @Test
    fun testTwoCommandsPipe() {
        stdin.writeLine("cat test | cat")
        main(emptyArray())
        for (line in fileContent) {
            assert(stdout.read() == line)
        }
    }
}
