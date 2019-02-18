package info.juanmendez.modernlearning.tags

import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.FILE,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@Tag("math")
@Test
annotation class MathTest