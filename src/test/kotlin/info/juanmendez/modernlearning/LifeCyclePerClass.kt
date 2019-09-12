package info.juanmendez.modernlearning

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LifeCyclePerClass {

    @BeforeAll
    fun beforeAll() {
        println("before all $this")
    }

    @BeforeEach
    fun beforeEach() {
        println("before for each $this")
    }

    @Test
    fun `my first test`() {
        assertTrue(listOf<String>().isEmpty())
    }

    @AfterEach
    fun afterEach() {
        println("after each $this")
    }

    @AfterAll
    fun afterAll() {
        println("after all $this")
    }
}