package info.juanmendez.modernlearning

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertTrue

@DisplayName("parent nesting class")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class NestingClasses {
    lateinit var bands: MutableList<String>

    @Nested
    @DisplayName("nested class 1st level")
    class FirstLevelNestedClass {
        lateinit var bands: MutableList<String>

        @BeforeEach
        fun doBeforeEach() {
            bands = mutableListOf()
        }

        @Test
        @Order(1)
        fun `assert empty`() {
            assertTrue(bands.isEmpty())
        }

        @Test
        @Order(2)
        fun `assert with exception`() {
            assertThrows<IndexOutOfBoundsException> { bands[0] }
        }

        @Nested
        @DisplayName("nested class 2nd level")
        class SecondLevelNestedClass {
            @BeforeEach
            fun doBeforeEach() {

            }
        }
    }
}