package architecture

/**
 * Base class for Clean architecture tests.
 *
 * @param prefix The prefix of the package names.
 */
abstract class CleanArchitectureSpec(prefix: String) : ArchitectureSpec(prefix) {
    private val domainLayer = assertLayer("domain")
    private val applicationLayer = assertLayer("application")
    private val interfacesLayer = assertLayer("interfaces")
    private val infrastructureLayer = assertLayer("infrastructure")

    @Test
    fun `architecture is Clean`() {
        assertArchitecture {
            domainLayer.dependsOnNothing()
            applicationLayer.dependsOn(domainLayer)
            interfacesLayer.dependsOn(applicationLayer, domainLayer, infrastructureLayer)
            infrastructureLayer.dependsOn(applicationLayer, domainLayer, interfacesLayer)
        }
    }
}
