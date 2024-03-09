package architecture

import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class CleanArchitectureSpec : AnnotationSpec(), ArchitectureSpec {
    override val prefix = "piperkt.services.multimedia"

    private val domainLayer = assertLayer("domain")
    private val applicationLayer = assertLayer("application")
    private val interfacesLayer = assertLayer("interfaces")
    private val infrastructureLayer = assertLayer("infrastructure")

    @Test
    fun `architecture is Clean`() {
        assertArchitecture {
            domainLayer.dependsOnNothing()
            applicationLayer.dependsOn(domainLayer)
            interfacesLayer.dependsOn(applicationLayer)
            infrastructureLayer.dependsOn(applicationLayer, domainLayer)
        }
    }
}
