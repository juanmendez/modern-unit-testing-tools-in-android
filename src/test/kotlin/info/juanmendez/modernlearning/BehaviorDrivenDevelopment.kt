package info.juanmendez.modernlearning

import de.mannodermaus.example.common.ALL_CARDS
import de.mannodermaus.example.common.Deck
import de.mannodermaus.example.common.Session
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals

/**
 * Sharing data between parent class and its children
 */
class BehaviorDrivenDevelopment {
    private lateinit var deck: Deck

    @BeforeEach
    fun onBeforeEach() {
        println("test.onBeforeEach")
        deck = Deck(ALL_CARDS)
    }

    @Nested // this child class is integrated in parent lifecycle
    inner class SessionTurnTests {
        private lateinit var session: Session

        @BeforeEach
        fun onBeforeEachChild() {
            println("test.SessionTurnTests.onBeforeEach")
            session = Session(deck, "Player 1")
        }

        @Test
        @DisplayName("your first test")
        fun `test 1`() {
            val turn = session.turn
            session.makeTurn()

            assertEquals(turn + 1, session.turn)
        }

        @Test
        @DisplayName("your second test")
        fun `test 2`() {
            val oldDeck = deck.size
            session.makeTurn()

            assertEquals(oldDeck, deck.size + 1)
        }

        @AfterEach
        fun onAfterEachChild() {
            println("test.SessionTurnTests.onAfterEachChild")
        }
    }

    @AfterEach
    fun onAfterEach() {
        println("test.onAfterEach")
    }
}