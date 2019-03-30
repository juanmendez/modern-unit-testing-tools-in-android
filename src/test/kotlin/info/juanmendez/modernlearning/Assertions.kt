package info.juanmendez.modernlearning

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows

class Assertions {
    lateinit var bands: MutableList<String>

    @BeforeEach
    fun doBeforeAll() {
        bands = mutableListOf("Guns n Roses", "Green Lung", "Motorhead")
    }

    @Test
    fun `assert all assertions`() {
        applyAssertAll()

        // grouping assertAll
        assertAll(
            "grouping assert alls", {
                applyAssertAll()
            }, {
                applyAssertAll()
            }
        )

        assertAll(
            "bands starting with a given letter", {
                assertEquals(bandStartingWithletter("G").size, 2)
            }, {
                assertTrue(bandStartingWithletter("M").isNotEmpty())
            }
        )

        assertAll(
            "all band names length are greater than 3", {
                bands.forEach {
                    assertTrue(it.length > 3)
                }
            }
        )
    }


    fun applyAssertAll() {
        assertAll(
            "great than", {
                assertEquals(1, 1, "1 is 1")
            }, {
                assertEquals(2, 2, "2 is 2")
            }
        )
    }

    fun bandStartingWithletter(letter: String): List<String> {
        return bands.filter { it.startsWith(letter) }
    }

    @Test
    fun `assert exception`() {
        val calculator = Calculator()
        val errorMessage = "https://youtu.be/gYMLwLTzGUg"
        val exception = ClassNotFoundException(errorMessage)

        val caughtException = assertThrows<Exception> {
            calculator.makeError(exception)
        }

        assertEquals(caughtException.message, errorMessage)

        /**
         * tried out assertThrows to expect another exception class, and this came back
         * org.opentest4j.AssertionFailedError: Unexpected exception type thrown ==>
         * Expected :<java.lang.ArrayStoreException>
         *     Actual   :<java.lang.ClassNotFoundException>
         */
    }
}