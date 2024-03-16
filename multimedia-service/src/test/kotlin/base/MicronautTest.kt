package base

import io.kotest.core.spec.style.FunSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest abstract class MicronautTest(body: FunSpec.() -> Unit) : FunSpec(body)
