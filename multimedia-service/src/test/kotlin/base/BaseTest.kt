package base

import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

sealed interface UnitTest {
    open class AnnotationSpec : io.kotest.core.spec.style.AnnotationSpec()

    open class FunSpec(body: io.kotest.core.spec.style.FunSpec.() -> Unit = {}) :
        io.kotest.core.spec.style.FunSpec(body)
}

sealed interface IntegrationTest {
    @MicronautTest open class AnnotationSpec : io.kotest.core.spec.style.AnnotationSpec()

    @MicronautTest
    open class FunSpec(body: io.kotest.core.spec.style.FunSpec.() -> Unit = {}) :
        io.kotest.core.spec.style.FunSpec(body)
}
