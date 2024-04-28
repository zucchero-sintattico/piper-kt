package base

import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

open class UnitTest : AnnotationSpec()

@MicronautTest open class IntegrationTest : AnnotationSpec()
