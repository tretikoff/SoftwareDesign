import org.junit.Test

class GrepTest : BashTest() {
    @Test
    fun testFindPattern() {
        stdin.writeLine("grep f.*line test")
        main(emptyArray())
        assert(stdout.read() == fileContent[0])
        assert(stdout.read() == fileContent[3])
    }

    @Test
    fun testFindSubstring() {
        stdin.writeLine("grep cond test")
        main(emptyArray())
        assert(stdout.read() == fileContent[1])
    }

    @Test
    fun testIgnoreCase() {
        val search = "eCOnD"
        stdin.writeLine("grep -i $search test")
        main(emptyArray())
        assert(stdout.read() == fileContent[1])
    }

    @Test
    fun testShowLinesAfter() {
        val search = "econd"
        stdin.writeLine("grep $search test -A1")
        main(emptyArray())
        assert(stdout.read() == fileContent[1])
        assert(stdout.read() == fileContent[2])
    }

    @Test
    fun testSearchesWord() {
        val line = fileContent[0]
        stdin.writeLine("grep ${line.split(" ").first()} test")
        main(emptyArray())
        assert(stdout.read() == line)
    }
}
