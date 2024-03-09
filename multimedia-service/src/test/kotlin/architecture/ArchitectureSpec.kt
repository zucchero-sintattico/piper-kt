package architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.DependencyRules
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import io.kotest.core.spec.style.AnnotationSpec

open class ArchitectureSpec(private val prefix: String) : AnnotationSpec() {
    fun asserLayer(name: String): Layer {
        return Layer(name, "$prefix.$name..")
    }

    fun assertArchitecture(block: DependencyRules.() -> Unit) {
        Konsist.scopeFromProject().assertArchitecture { block() }
    }
}
