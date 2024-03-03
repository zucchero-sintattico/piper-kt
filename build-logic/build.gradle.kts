plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.plugins.allOpen.asDependency())
    implementation(libs.plugins.detekt.asDependency())
    implementation(libs.plugins.kotlin.asDependency())
    implementation(libs.plugins.kover.asDependency())
    implementation(libs.plugins.ksp.asDependency())
    implementation(libs.plugins.ktfmt.asDependency())
    implementation(libs.plugins.micronaut.app.asDependency())
    implementation(libs.plugins.micronaut.aot.asDependency())
    implementation(libs.plugins.micronaut.test.asDependency())
    implementation(libs.micronaut.buildtools)
    implementation(libs.plugins.shadow.asDependency())
}

fun Provider<PluginDependency>.asDependency(): Provider<String> =
    this.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }
