package info.juanmendez.modernlearning

import de.mannodermaus.example.common.Card
import de.mannodermaus.example.common.Rank
import de.mannodermaus.example.common.Suit
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.RepeatedTest
import org.junit.jupiter.api.RepetitionInfo
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.converter.ConvertWith
import org.junit.jupiter.params.converter.SimpleArgumentConverter
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ValueSourceTest {
    companion object {
        @JvmStatic
        fun testData(): List<Arguments> {
            return listOf(
                Arguments.of(".java", "java"),
                Arguments.of(".kt", "kotlin"),
                Arguments.of(".js", "javascript"),
                Arguments.of( ".swift", "swift")
            )
        }

        @JvmStatic
        fun parse(value: String): Card {
            val suitValue = value.last().toString()
            val rankValue = value.dropLast(1)

            return Card(rank = Rank.parse(rankValue), suit = Suit.parse(suitValue))
        }
    }

    /**
     * this way of testing helps to track which iterations failed
     * by the way all of the iterations are executed no matter if any of them fail
     */
    @RepeatedTest(value = 21)
    fun `doing the magic`(info: RepetitionInfo) {
        val aceValue = Rank.Ace.value(currentSum = info.currentRepetition)

        when(info.currentRepetition) {
            in 1..10 -> {
                assertEquals(aceValue, 11)
            }

            else -> {
                assertEquals(aceValue, 1)
            }
        }
    }


    /**
     * all values are tested, and @ParameterizedTest severs the same purpose
     * as @DisplayName. You can include the current value using {0}.
     */
    @ParameterizedTest(name = "currently testing {0}")
    @ValueSource(strings=["a", "b", "c", "", "d", "e", "f"])
    fun `do the test`(value: String) {
        assertTrue(value.isNotEmpty())
    }

    @ParameterizedTest(name = "currently testing {0}")
    @EnumSource(Suit::class)
    fun `go through each enum type`(suit: Suit) {
        assertEquals(suit.toString().length, 1)
    }

    @ParameterizedTest(name = "current test {1}")
    @MethodSource("testData")
    fun `test agains parameters provided`(type: String, language: String) {
        assertTrue(type.startsWith("."))
        assertTrue(language.isNotEmpty())
    }

    @ParameterizedTest(name = "Parsing {0} returns a totally valid Card")
    @ValueSource(strings = ["2♥", "9♦", "10♥", "J♣", "Q♣", "K♦", "A♥"])
    fun `implicit converter`(card: Card) {
        assertNotNull(card)
    }

    @ParameterizedTest(name = "Parsing {0} returns a totally valid Card")
    @ValueSource(strings = ["2♥", "9♦", "10♥", "J♣", "Q♣", "K♦", "A♥"])
    fun `explicit converter`(@ConvertWith(RankConverter::class) card: Card) {
        assertNotNull(card)
    }

    @ParameterizedTest(name = "{0}:{1}")
    @FileSource("inputs.json")
    fun test(map: Map<String, String>) {
        assertNotNull(map)
    }
}

class RankConverter : SimpleArgumentConverter() {
    override fun convert(
        source: Any,
        targetType: Class<*>
    ): Card {
        val value = source as String
        val suitValue = value.last().toString()
        val rankValue = value.dropLast(1)

        return Card(rank = Rank.parse(rankValue), suit = Suit.parse(suitValue))
    }
}