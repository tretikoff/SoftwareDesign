import org.junit.Test

class QuotesTest : BashTest() {

    @Test
    fun testSingleQuote() {
        val str = "hello"
        stdin.writeLine("echo '$str'")
        main(emptyArray())
        assert(stdout.read() == str)
    }

    @Test
    fun testDoubleQuote() {
        val str = "hello"
        stdin.writeLine("echo \"$str\"")
        main(emptyArray())
        assert(stdout.read() == str)
    }

    @Test
    fun testDoubleQuoteSubstitutesVariable() {
        val str = "hello"
        stdin.writeLine("str=$str")
        stdin.writeLine("echo \"\$str\"")
        main(emptyArray())
        assert(stdout.read() == str)
    }
    @Test
    fun testSingleQuoteDoesNotSubstituteVariable() {
        val str = "hello"
        stdin.writeLine("str=$str")
        stdin.writeLine("echo \"\$str\"")
        main(emptyArray())
        assert(stdout.read() == str)
    }
}
