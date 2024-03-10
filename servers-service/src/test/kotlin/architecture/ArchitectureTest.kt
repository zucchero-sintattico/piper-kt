package architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import org.junit.jupiter.api.Test

class ArchitectureTest {
    @Test
    fun `architecture layers have dependencies correct`() {
        Konsist.scopeFromProject() // Define the scope containing all Kotlin files present i
            .assertArchitecture {
                val packagePrefix = "piperkt.services.servers"
                val domain = Layer("Domain", "$packagePrefix.domain..")
                val application = Layer("Application", "$packagePrefix.application..")
                val interfaces = Layer("Interfaces", "$packagePrefix.interfaces..")
                val infrastructure = Layer("Infrastructure", "$packagePrefix.infrastructure..")
                domain.dependsOnNothing()
                application.dependsOn(domain)
                interfaces.dependsOn(application)
                infrastructure.dependsOn(application, domain)
            }
    }
}
