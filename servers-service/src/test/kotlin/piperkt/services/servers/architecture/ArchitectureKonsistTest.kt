package piperkt.services.servers.architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.jupiter.api.Test

class ArchitectureKonsistTest {
    @Test
    fun `architecture layers have dependencies correct`() {
        Konsist.scopeFromProject() // Define the scope containing all Kotlin files present i
            .assertArchitecture { // Assert architecture
                // Define layers
                val infrastructure =
                    Layer("Infrastructure", "piperkt.services.servers.infrastructure..")
                val application = Layer("Application", "piperkt.services.servers.application..")
                val domain = Layer("Domain", "piperkt.services.servers.domain..")
                // Define architecture assertions
                infrastructure.dependsOn(application)
                infrastructure.dependsOn(domain)
                application.dependsOn(domain)
                domain.dependsOnNothing()
            }
    }
}
