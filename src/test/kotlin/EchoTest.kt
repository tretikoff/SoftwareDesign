import org.junit.Test

class EchoTest : BashTest() {
    @Test
    fun echoStr() {
        val str = "hello"
        stdin.writeLine("echo $str")
        main(emptyArray())
        assert(stdout.read() == str)
    }

    @Test
    fun echoEmpty() {
        stdin.writeLine("echo")
        main(emptyArray())
        assert(stdout.read().isNullOrEmpty())
    }
}
