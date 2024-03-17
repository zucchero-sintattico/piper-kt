package architecture

import base.Test

class FrameworkIndependenceTest : ArchitectureSpec, Test.Unit.AnnotationSpec() {
    override val prefix = "piperkt.services.multimedia"

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
