package architecture

import base.Test
import io.kotest.core.spec.style.AnnotationSpec

class CleanArchitectureSpec : Test.Unit, ArchitectureSpec, AnnotationSpec() {
    override val prefix: String = "piperkt.services.multimedia"
    private val domainLayer = super.assertLayer("domain")
    private val applicationLayer = assertLayer("application")
    private val interfacesLayer = assertLayer("interfaces")
    private val infrastructureLayer = assertLayer("infrastructure")

    @Test
    fun `architecture is Clean`() {
        assertArchitecture {
            domainLayer.dependsOnNothing()
            applicationLayer.dependsOn(domainLayer)
            interfacesLayer.dependsOn(applicationLayer, domainLayer)
            infrastructureLayer.dependsOn(applicationLayer, domainLayer)
        }
    }
}
