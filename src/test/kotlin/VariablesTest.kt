import org.junit.Test

class VariablesTest : BashTest() {

    @Test
    fun testVariableSet() {
        stdin.writeLine("var=test")
        stdin.writeLine("cat \$var")
        main(emptyArray())
        for (line in fileContent) {
            assert(stdout.read() == line)
        }
//        assert(stdout.empty())
    }
}
