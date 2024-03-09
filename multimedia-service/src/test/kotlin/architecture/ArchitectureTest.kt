package architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class ArchitectureTest : AnnotationSpec() {
    @Test
    fun `architecture is Clean`() {
        Konsist.scopeFromProject().assertArchitecture {
            val packagePrefix = "piperkt.services.multimedia"
            val domainLayer = Layer("domain", "$packagePrefix.domain..")
            val applicationLayer = Layer("application", "$packagePrefix.application..")
            val presentationLayer = Layer("presentation", "$packagePrefix.presentation..")
            val infrastructureLayer = Layer("infrastructure", "$packagePrefix.infrastructure..")

            domainLayer.dependsOnNothing()
            applicationLayer.dependsOn(domainLayer)
            presentationLayer.dependsOn(applicationLayer) // + micronaut
            infrastructureLayer.dependsOn(applicationLayer, domainLayer) // + micronaut
        }
    }
}
