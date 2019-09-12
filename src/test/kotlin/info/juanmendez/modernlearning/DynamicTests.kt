package info.juanmendez.modernlearning

import de.mannodermaus.example.common.Card
import de.mannodermaus.example.common.Rank
import de.mannodermaus.example.common.Suit
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.DynamicContainer.dynamicContainer
import org.junit.jupiter.api.DynamicTest.dynamicTest
import java.util.stream.IntStream
import java.util.stream.Stream

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DynamicTests {
    @BeforeAll
    fun `beforeAll`() {
        println("before all")
    }

    @BeforeEach
    fun `before each`() {
        println("before each")
    }

    /**
     * for the lifecycle all dynamic tests happen as if they were part in one test
     */
    @TestFactory
    fun `returning stream of DynamicNodes`(): Stream<DynamicNode> {
        return Stream.of(*Suit.values())
            .map { suit ->
                dynamicContainer(
                    "Dynamic tests with ${suit.toLongString()}",
                    IntStream.range(2, 10).mapToObj { number ->
                        val rank = Rank.Num(number)
                        val cardConcat = "$number$suit"
                        dynamicTest("$cardConcat is a valid card") {
                            val card = Card(rank, suit)
                            println("test 1")
                            assertEquals("$cardConcat", card.toString(), "dynamic test")
                        }
                    })
            }
    }

    @TestFactory
    fun `returning list of DynamicNodes`(): List<DynamicNode> {
        return Suit.values()
            .map { suit ->
                dynamicContainer(
                    "Dynamic tests with ${suit.toLongString()}",
                    IntStream.range(2, 10).mapToObj { number ->
                        val rank = Rank.Num(number)
                        val cardConcat = "$number$suit"
                        dynamicTest("$cardConcat is a valid card") {
                            println("test 2")
                            val card = Card(rank, suit)
                            assertEquals("$cardConcat", card.toString(), "by DynamicNodes")
                        }
                    })
            }
    }

    @TestFactory
    fun `returning one DynamicNode`(): DynamicNode {
        val list: List<Map<String, String>> = MoshiUtils().fromJsonList("inputs.json") ?: listOf()

        return dynamicContainer("iterating list", list.map { map ->
            dynamicTest("Testing ${map}") {
                println("test 3")
                assertFalse(map.isEmpty(), "by one DynamicNode")
            }
        })
    }

    @AfterEach
    fun `after each`() {
        println("after each")
    }

    @AfterAll
    fun `after all`() {
        println("after all")
    }
}