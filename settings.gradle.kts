/*
 * This file was generated by the Gradle 'init' task.
 *
 * The settings file is used to specify which projects to include in your build.
 * For more detailed information on multi-project builds, please refer to https://docs.gradle.org/8.6/userguide/multi_project_builds.html in the Gradle documentation.
 */

rootProject.name = "piper-kt"

pluginManagement { includeBuild("build-logic") }

dependencyResolutionManagement { repositories { mavenCentral() } }

plugins {
    id("org.danilopianini.gradle-pre-commit-git-hooks") version "2.0.5"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.8.0"
}

gitHooks {
    // Configuration
    commitMsg { conventionalCommits() }
    preCommit { tasks("classes", "ktfmtCheck", "detekt", "spotlessCheck", "--parallel") }
    createHooks(overwriteExisting = true) // actual hooks creation
}

include("bdd")

include("commons")

include("users-service")

include("servers-service")

include("multimedia-service")

include("friendships-service")

include("notifications-service")

include("frontend-service")
