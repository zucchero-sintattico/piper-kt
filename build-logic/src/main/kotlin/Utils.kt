import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider

fun VersionCatalog.getLibrary(name: String): Provider<MinimalExternalModuleDependency> =
    findLibrary(name).get()

fun VersionCatalog.getBundle(name: String): Provider<ExternalModuleDependencyBundle> =
    findBundle(name).get()
