package info.juanmendez.modernlearning

import org.junit.jupiter.api.*
import org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS
import org.junit.jupiter.api.parallel.Execution
import org.junit.jupiter.api.parallel.ExecutionMode
import org.junit.jupiter.api.parallel.ExecutionMode.CONCURRENT

/**
 * in this example our test executes in multiple threads, instead of single thread
 * if you want to do the same for all tests, in your vm options include the following
 * -Djunit.jupiter.execution.parallel.enabled=true
 * -Djunit.jupiter.execution.parallel.mode.default=concurrent
 *
 * if we want to run tests by a stack, we do that setting strategy as dynamic
 * -Djunit.jupiter.execution.parallel.config.strategy=dynamic
 * we can multiple the number of tests by ( number of cores * factor )
 * -Djunit.jupiter.execution.parallel.config.dynamic.factor=2
 * so for example 8 tests are running when one is finished then the nineth test runs
 *
 * if we want to run tests in batches we do using fixed strategy
 * -Djunit.jupiter.execution.parallel.config.strategy=fixed
 *
 * we can set the length of our batches
 * -Djunit.jupiter.execution.parallel.config.fixed.parallelism=4
 */

@TestInstance(PER_CLASS)
@Execution(CONCURRENT)
class Parallelism {
    private var start: Long = 0

    @BeforeAll
    fun beforeAll() {
        start = System.nanoTime()
        println("Running on ${Runtime.getRuntime().availableProcessors()} cores")
    }

    @AfterAll
    fun afterAll() {
        val end = System.nanoTime()
        println("This took ${(end - start) / 1_000_000} ms")
    }

    // Switch to SAME_THREAD with parallel execution enabled for the default behavior.
    @Execution(CONCURRENT)
    @TestFactory
    fun slowTests() = LongRange(5, 20)
        .map { num ->
            DynamicTest.dynamicTest("Slow Test $num") {
                println("Executing Test $num on thread ${Thread.currentThread()}")
                Thread.sleep(num * 1_000)
            }
        }
}