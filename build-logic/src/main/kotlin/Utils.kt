import org.gradle.api.artifacts.ExternalModuleDependencyBundle
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider

fun VersionCatalog.getLibrary(name: String): Provider<MinimalExternalModuleDependency> = findLibrary(name).get()

fun VersionCatalog.getBundle(name: String): Provider<ExternalModuleDependencyBundle> = findBundle(name).get()

object JavaVersion {
    const val asInt: Int = 17
    val asVersion: org.gradle.api.JavaVersion = org.gradle.api.JavaVersion.toVersion(asInt)
}
