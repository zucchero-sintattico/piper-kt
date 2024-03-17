package base

import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

sealed interface Test {

    sealed interface Unit : Test {
        abstract class FunSpec(body: io.kotest.core.spec.style.FunSpec.() -> kotlin.Unit) :
            io.kotest.core.spec.style.FunSpec(body)

        abstract class AnnotationSpec : io.kotest.core.spec.style.AnnotationSpec(), Unit
    }

    sealed interface Integration : Test {
        @MicronautTest
        abstract class FunSpec(body: io.kotest.core.spec.style.FunSpec.() -> kotlin.Unit) :
            io.kotest.core.spec.style.FunSpec(body), Integration

        @MicronautTest
        abstract class AnnotationSpec : io.kotest.core.spec.style.AnnotationSpec(), Integration
    }
}
