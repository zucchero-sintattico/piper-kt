package architecture

import base.Test
import io.kotest.core.spec.style.AnnotationSpec

class CleanArchitectureSpec : Test.Unit, ArchitectureSpec, AnnotationSpec() {
    override val prefix: String = "piperkt.services.friendship"
    private val domainLayer = super.assertLayer("domain")
    private val applicationLayer = assertLayer("application")
    private val infrastructureLayer = assertLayer("infrastructure")

    @Test
    fun `architecture is Clean`() {
        assertArchitecture {
            domainLayer.dependsOnNothing()
            applicationLayer.dependsOn(domainLayer)
            infrastructureLayer.dependsOn(applicationLayer, domainLayer)
        }
    }
}
