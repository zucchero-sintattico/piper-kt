package base

import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.core.spec.style.FunSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

sealed interface Test {
    abstract class Offline(body: FunSpec.() -> Unit) : FunSpec(body)

    abstract class OfflineAnnotation : AnnotationSpec()

    @MicronautTest abstract class Online(body: FunSpec.() -> Unit) : FunSpec(body)

    @MicronautTest abstract class OnlineAnnotation : AnnotationSpec()
}
