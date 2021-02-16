import org.junit.Test

class CatTest : BashTest() {
    @Test
    fun catFileInput() {
        stdin.writeLine("cat test")
        main(emptyArray())
        for (line in fileContent) {
            assert(stdout.read() == line)
        }
    }
}
