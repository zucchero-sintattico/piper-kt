plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.plugins.detekt.asDependency())
    implementation(libs.plugins.kotlin.asDependency())
    implementation(libs.plugins.kover.asDependency())
    implementation(libs.plugins.ktfmt.asDependency())
}

fun Provider<PluginDependency>.asDependency(): Provider<String> =
    this.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
