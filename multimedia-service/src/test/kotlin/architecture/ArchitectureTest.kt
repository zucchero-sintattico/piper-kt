package architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class ArchitectureTest : AnnotationSpec() {
    @Test
    fun `test if the architecture is correct`() {
        Konsist.scopeFromProject().assertArchitecture {
            val packagePrefix = "piperkt.services.multimedia"
            val domainLayer = Layer("domain", "$packagePrefix.domain..")
            val applicationLayer = Layer("application", "$packagePrefix.application..")
            val infrastructureLayer = Layer("infrastructure", "$packagePrefix.infrastructure..")

            domainLayer.dependsOnNothing()
            applicationLayer.dependsOn(domainLayer)
            infrastructureLayer.dependsOn(applicationLayer, domainLayer)
        }
    }
}
