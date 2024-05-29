# Project Structure & Build System

The build system is a set of tools and practices used to automate the process of building the project.

The project uses **Gradle** as build system.

## Project Structure

The team decided to use *mono-repository, multi project* structure.
Subprojects are the following ones:

- `architecture-tests (jvm)`: contains test for static structural code analysis of projects.
- `bdd (jvm)`: contains the Behavior-driven development tests.
- `commons (multiplatform)`: contains common code for all the projects.
- `events (multiplatform)`: contains the events shared between the services.
- `friendships-service (jvm)`: contains the friendships microservice.
- `frontend-service (node)`: contains the frontend microservice.
- `notifications-service (node)`: contains the notifications microservice.
- `multimedia-service (jvm)`: contains the multimedia microservice.
- `servers-service (jvm)`: contains the servers microservice.
- `users-service (jvm)`: contains the users microservice.

## Dependencies declaration

In order to collect all the dependencies in a single place, the project uses the `Version Catalog`.
You can find the file in `gradle/libs.versions.toml`.

This method allows to declare dependencies in `build.gradle.kts` files using the following syntax:

```kotlin
dependencies {
    implementation(libs.kotlin)
    implementation(libs.micronaut)
    ...
}
```

> **Note**: Many library version are not specified in the file (e.g. **Micronaut** sub dependencies), because they are managed by the framework itself, using the [`bom` (Bill-Of-Materials)](https://micronaut-projects.github.io/micronaut-platform/latest/guide/) feature.

Unfortunately, the `Version Catalog` is not injected in `build-logic` and plugin must be in class path if they are applied from a `convention plugin`.
Here the proposed workarounds:

- Add the `Version Catalog` to the `build-logic` project:
```kotlin
// build-logic/settings.gradle.kts
dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}
```

- Adding plugins as dependencies in the `build-logic` project:
```kotlin
// build-logic/build.gradle.kts
fun Provider<PluginDependency>.asDependency(): Provider<String> =
    this.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }

dependencies {
    implementation(libs.plugins.detekt.asDependency())
    implementation(libs.plugins.kotlin.asDependency())
    ...
}
```

- Adding plugins in `convention plugin` with name, without version (it takes the version from project dependencies):
```kotlin
// build-logic/src/main/kotlin/kotlin-base.gradle.kts
plugins {
    kotlin("jvm")
    id("io.gitlab.arturbosch.detekt")
}
```

- Create the `Version Catalog` in the `convention plugin` project:
```kotlin
val catalog: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

fun VersionCatalog.getLibrary(name: String): Provider<MinimalExternalModuleDependency> =
    findLibrary(name).get()

dependencies {
    testImplementation(catalog.getLibrary("konsist"))
    testImplementation(catalog.getLibrary("kotest"))
}
```

## Shared build logic

Shared build logic is achieved using [shared gradle conventions plugins](https://docs.gradle.org/current/samples/sample_sharing_convention_plugins_with_build_logic.html).
This allows to share common build logic between the projects, applying the defined plugins.
Microservices project based on micronaut share many dependencies and configurations, so this approach helps to keep the build logic DRY, defining it in a single place.

It is possible to apply the shared build logic by adding the following line to the `settings.gradle.kts` file of the project:

```kotlin
pluginManagement { includeBuild("build-logic") }
```

As follow a conceptual example of dependencies in every micronaut project:

```kotlin
// build-logic/src/main/kotlin/micronaut.gradle.kts
plugins {
    id("org.jetbrains.kotlin.jvm")
    id("io.micronaut.application")
    ...
}

dependencies { ... }

micronaut { ... }

// Shared tasks
```

This configuration is defined in the `build-logic` project, and applied everywhere it is needed.

The result `build.gradle.kts` file will look like this:

```kotlin
// servers-service/build.gradle.kts
plugins {
    id("micronaut")
}

// users-service/build.gradle.kts
plugins {
    id("micronaut")
}
```

## Typescript Gradle Plugin

Some sub projects are written in Typescript, so, for a didactic purpose, it's been create a Gradle plugin to manage the Typescript build.
It's not intended to be a fully wrapper for `tsc`, `node`, `npm` and so on, but only a simple way to integrate Typescript projects into piper-kt project build flow.

You can find the code in a dedicated GitHub repository about [Typescript Gradle Plugin](https://github.com/zucchero-sintattico/typescript-gradle-plugin) with the documentation available in the `README.md` file.

It's been published on:

- [Gradle Plugin Portal](https://plugins.gradle.org/plugin/io.github.zucchero-sintattico.typescript-gradle-plugin)
- [GitHub Packages](https://github.com/zucchero-sintattico/typescript-gradle-plugin/packages/)
- [Maven Central Repository](https://central.sonatype.com/artifact/io.github.zucchero-sintattico/typescript-gradle-plugin)

To apply the plugin in a project, you can add the following line in the `build.gradle.kts` file:

```kotlin
plugins {
    id("io.github.zucchero-sintattico.typescript-gradle-plugin") version "<version>"
}
```

This plugin add gradle lifecycle tasks like `build` and `check` to the Typescript project, in order to easy integrate typescript projects (notifications, frontend) into build flow.

### Additional tasks

The [*Micronaut Gradle Plugin*](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/) used in micronaut subprojects, adds support for docker image context creation, docker file, and other related things.

In order to Build the Docker image are created similar tasks for non micronaut projects, each one with specific steps given the different build flow.

In particular the tasks are `buildLayers` and `dockerfile`.

Here a example:

```kotlin
tasks.named("buildLayers") {
    doLast {
        copy {
            from("build/dist")
            into("build/docker/main/dist")
        }
        copy {
            from("node_modules")
            into("build/docker/main/node_modules")
        }
    }
}

tasks.register("dockerfile") {
    doLast {
        copy {
            from("Dockerfile")
            into("build/docker/main")
        }
    }
}
```
