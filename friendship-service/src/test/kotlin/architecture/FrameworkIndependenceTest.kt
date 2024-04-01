package architecture

import base.Test
import io.kotest.core.spec.style.AnnotationSpec

class FrameworkIndependenceTest : Test.Unit, ArchitectureSpec, AnnotationSpec() {
    override val prefix = "piperkt.services.friendship"

    private val frameworks = listOf("io.micronaut", "jakarta")

    @Test
    fun `domain layer doesn't depend on frameworks`() {
        assertPackageDoesNotDependOnFrameworks("domain", frameworks)
    }

    @Test
    fun `application layer doesn't depend on frameworks`() {
        assertPackageDoesNotDependOnFrameworks("application", frameworks)
    }
}
