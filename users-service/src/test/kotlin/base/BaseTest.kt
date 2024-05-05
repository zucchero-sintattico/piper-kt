package base

import io.micronaut.test.annotation.TransactionMode
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

sealed interface UnitTest {
    open class AnnotationSpec : io.kotest.core.spec.style.AnnotationSpec()

    open class FunSpec(body: io.kotest.core.spec.style.FunSpec.() -> Unit = {}) :
        io.kotest.core.spec.style.FunSpec(body)
}

sealed interface IntegrationTest {
    @MicronautTest(rollback = true, transactionMode = TransactionMode.SINGLE_TRANSACTION)
    open class AnnotationSpec : io.kotest.core.spec.style.AnnotationSpec()

    @MicronautTest(rollback = true, transactionMode = TransactionMode.SINGLE_TRANSACTION)
    open class FunSpec(body: io.kotest.core.spec.style.FunSpec.() -> Unit = {}) :
        io.kotest.core.spec.style.FunSpec(body)
}
