package architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.DependencyRules
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer

interface ArchitectureSpec {

    val prefix: String

    fun assertLayer(name: String): Layer {
        return Layer(name, "$prefix.$name..")
    }

    fun assertArchitecture(block: DependencyRules.() -> Unit) {
        Konsist.scopeFromProject().assertArchitecture { block() }
    }
}
