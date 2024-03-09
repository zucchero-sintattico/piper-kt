package architecture

import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class CleanArchitectureSpec : ArchitectureSpec("piperkt.services.multimedia") {

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
