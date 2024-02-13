/*
 * This file was generated by the Gradle 'init' task.
 *
 * This is a general purpose Gradle build.
 * To learn more about Gradle by exploring our Samples at https://docs.gradle.org/8.6/samples
 */

plugins {
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.kover)
    alias(libs.plugins.gitSemVer)
}

gitSemVer {
    // Your configuration
    assignGitSemanticVersion()
}

dependencies {
    project.subprojects.forEach {
        kover(it)
    }
}

allprojects {
    group = "piperchat"
}

subprojects {
    apply(plugin = "org.jetbrains.dokka")
}
