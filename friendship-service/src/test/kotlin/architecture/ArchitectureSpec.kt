package architecture

import com.lemonappdev.konsist.api.Konsist
import com.lemonappdev.konsist.api.architecture.DependencyRules
import com.lemonappdev.konsist.api.architecture.KoArchitectureCreator.assertArchitecture
import com.lemonappdev.konsist.api.architecture.Layer
import io.kotest.matchers.shouldBe

interface ArchitectureSpec {

    val prefix: String

    fun assertLayer(name: String): Layer {
        return Layer(name, "$prefix.$name..")
    }

    fun assertArchitecture(block: DependencyRules.() -> Unit) {
        Konsist.scopeFromProject().assertArchitecture { block() }
    }

    fun assertPackageDoesNotDependOnFrameworks(
        packageName: String,
        frameworks: List<String> = emptyList()
    ) {
        Konsist.scopeFromPackage("$prefix.$packageName", sourceSetName = "main").files.forEach {
            file ->
            val dependencies = file.imports.map { it.name }
            val forbidden =
                dependencies.filter { dependency ->
                    frameworks.any { framework -> dependency.startsWith(framework) }
                }
            forbidden shouldBe emptyList()
        }
    }
}