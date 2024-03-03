import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider

fun VersionCatalog.getLibrary(name: String): Provider<MinimalExternalModuleDependency> = findLibrary(name).get()

object JavaVersion {
    val asInt: Int = 17
    val asVersion: org.gradle.api.JavaVersion = org.gradle.api.JavaVersion.toVersion(asInt)
}
