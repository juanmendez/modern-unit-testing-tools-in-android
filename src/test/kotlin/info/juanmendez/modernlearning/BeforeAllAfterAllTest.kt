/**
 * Copyright 2015-2018 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 *
 * @see https://github.com/junit-team/junit5-samples
 */

package info.juanmendez.modernlearning

import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@DisplayName("\uD83C\uDF54")
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //annotation required in order to work
class BeforeAllAfterAllTest {
    private val calculator = Calculator()
    private var tests = 0

    @BeforeAll
    fun beforeAll() {
        assertEquals(tests, 0, "there are zero tests before all tests")
    }

    @AfterAll
    fun afterAll() {
        assertEquals(tests, 6, "there are six tests after all tests")
    }

    @Test
    fun `1 + 1 = 2`() {
        tests++
        assertEquals(2, calculator.add(1, 1), "1 + 1 should equal 2")
    }

    @ParameterizedTest(name = "{0} + {1} = {2}")
    @CsvSource(
        "0,    1,   1",
        "1,    2,   3",
        "49,  51, 100",
        "1,  100, 101"
    )
    fun `sum values`(first: Int, second: Int, expectedResult: Int) {
        tests++

        assertEquals(expectedResult, calculator.add(first, second)) {
            "$first + $second should equal $expectedResult"
        }
    }

    @Test
    @DisplayName("╯°□°）╯")
    fun `division by zero`() {
        tests++

        val exception = assertThrows<AssertionError> {
            calculator.div(1, 0)
        }

        assertEquals("Division by Zero", exception.message)
    }

    /**
     * This test fails without @Disabled annotation
     *
     * org.opentest4j.AssertionFailedError:
     * Expected :2
     * Actual   :2.0
     */
    @Disabled("this test is skipped!")
    @Test
    fun `disabled test that fails`() {
        tests++

        assertEquals(2, calculator.div(8, 4))
    }
}