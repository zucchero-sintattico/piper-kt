package architecture

import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class CleanArchitectureSpec : AnnotationSpec(), ArchitectureSpec {
    override val prefix = "piperkt.services.multimedia"

    private val domainLayer = asserLayer("domain")
    private val applicationLayer = asserLayer("application")
    private val interfacesLayer = asserLayer("interfaces")
    private val infrastructureLayer = asserLayer("infrastructure")

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
