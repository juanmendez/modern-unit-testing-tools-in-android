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

import info.juanmendez.modernlearning.tags.Math
import info.juanmendez.modernlearning.tags.MathTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TaggingTest {
    private lateinit var calculator: Calculator

    @BeforeEach
    fun beforeEach() {
        calculator = Calculator()
    }

    @Math
    @Test
    fun `verify testing on math tag`() {
        assertEquals(calculator.div(10, 5), 2.0, "ensure values is 2")
    }

    @MathTest
    fun `combining Math and Test annotations`() {
        assertEquals(calculator.div(10, 5), 2.0, "ensure values is 2")
    }
}