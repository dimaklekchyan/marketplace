import org.junit.Test
import kotlin.test.assertEquals

class SomeTestM1L1 {
    @Test
    fun runWrongTest() {
        assertEquals(3, 1 + 3)
    }

    @Test
    fun runCorrectTest() {
        assertEquals(5, 2 + 3)
    }
}