package info.juanmendez.modernlearning

import de.mannodermaus.example.common.ALL_CARDS
import de.mannodermaus.example.common.Rank
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assumptions.assumeFalse
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable
import org.junit.jupiter.api.condition.EnabledIfSystemProperty

class SkippingTests {

    /**
     * In direct contrast to failed assertions, failed assumptions do not result in a test failure;
     * rather, a failed assumption results in a test being aborted.
     * Assumptions are typically used whenever it does not make sense to continue execution of a given test method
     * — for example, if the test depends on something that does not exist in the current runtime environment.
     */
    @Test
    @DisplayName("seeing how assumptions work")
    fun `see how assumptions work`() {
        val allAces = ALL_CARDS.filter { it.rank == Rank.Ace }

        assumeFalse(allAces.count() == 4)

        if (ALL_CARDS.size == 52) {
            throw AssertionError()
        }
    }

    @Test
    @Disabled
    @DisplayName("test is disabled")
    fun `test will be skipped`() {
        throw AssertionError()
    }

    /**
     * Go to edit-configurations, select this test, and make sure vm options reads as follows
     * -ea -Dconfig.value=true
     */
    @Test
    @EnabledIfSystemProperty(named = "config.value", matches = "true")
    fun `run if system property is set`() {
        assertEquals(1, 1)
    }

    @Test
    @EnabledIfEnvironmentVariable(named = "MY_ENV_VAR", matches = "true")
    fun `run if environment property is set`() {
        assertEquals(1, 1)
    }

    @Test
    @Tag("slow")
    fun `test by tag`() {
        assertEquals(1, 1)
    }
}