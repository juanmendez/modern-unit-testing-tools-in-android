package info.juanmendez.modernlearning

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT
import org.junit.jupiter.api.parallel.ResourceAccessMode.READ
import org.junit.jupiter.api.parallel.ResourceAccessMode.READ_WRITE
import org.junit.jupiter.api.parallel.ResourceLock
import java.util.*

/**
 * this is the solution to how to shared resources while testing in parallel mode.
 * just like sequence testing, the resource is expercted to start at the initial state
 * for each test. In this demo while locking the resource, it is set to its initial test
 * after each test.
 */
@TestInstance(PER_CLASS)
@Execution(CONCURRENT)
class ParallelismWithSharedResources {

    // Copy of the original System.getProperties(),
    // used to prevent mutable, shared state after these tests have executed.
    private lateinit var originalProperties: Properties

    @BeforeEach
    fun beforeEach() {
        // Save the current System properties,
        // because the tests in this class will modify them
        originalProperties = Properties()
        originalProperties.putAll(System.getProperties())
    }

    @AfterEach
    fun afterEach() {
        // Restore the original System properties
        System.setProperties(originalProperties)
    }

    @Test
    @ResourceLock("System Properties", mode = READ)
    fun checkThatPropertyIsNullByDefault() {
        assertNull(System.getProperty("custom.property"))
    }

    @Test
    @ResourceLock("System Properties", mode = READ_WRITE)
    fun checkThatPropertyIsSetCorrectlyToHello() {
        System.setProperty("custom.property", "hello")
        assertEquals("hello", System.getProperty("custom.property"))
    }

    // Switch to SAME_THREAD with parallel execution enabled for the default behavior.
    @Execution(CONCURRENT)
    @ResourceLock("System Properties", mode = READ_WRITE)
    @TestFactory
    fun slowTests() = LongRange(5, 20)
        .map { num ->
            DynamicTest.dynamicTest("Slow Test $num") {
                System.setProperty("custom.property", num.toString())
                assertEquals(num.toString(), System.getProperty("custom.property"))

                Thread.sleep(num * 400)
            }
        }

    @Test
    @ResourceLock("System Properties", mode = READ_WRITE)
    fun checkThatPropertyIsSetCorrectlyToWorld() {
        System.setProperty("custom.property", "world")
        assertEquals("world", System.getProperty("custom.property"))
    }
}
