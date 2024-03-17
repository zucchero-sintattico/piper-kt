package base

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.FunSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

sealed interface Test {
    abstract class UnitFunSpec(body: FunSpec.() -> Unit) : FunSpec(body)

    abstract class UnitAnnotationSpec : AnnotationSpec()

    @MicronautTest abstract class IntegrationFunSpec(body: FunSpec.() -> Unit) : FunSpec(body)

    @MicronautTest abstract class IntegrationAnnotationSpec : AnnotationSpec()
}
