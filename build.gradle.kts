import org.gradle.configurationcache.extensions.capitalized

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
    alias(libs.plugins.spotless)
}

gitSemVer {
    // Your configuration
    assignGitSemanticVersion()
}

val notKotlinProjects = listOf(
    "frontend-service",
    "notifications-service",
)

dependencies {
    project.subprojects
        .filter { it.name !in notKotlinProjects }
        .forEach { kover(it) }
}

allprojects {
    group = "piperkt"
}

subprojects {
    if (name !in notKotlinProjects) {
        apply(plugin = "org.jetbrains.dokka")
    }
}

spotless {
    yaml {
        target("**/*.yml", "**/*.yaml")
        targetExclude(
            "**/build/**",
            "**/node_modules/**",
            "**/helm-chart/**",
            "auth.yml",
            "resources/report/**",
        )
        prettier()
    }
}

val openApiProjects = listOf(
    "friendships-service",
    "servers-service",
)

tasks.register<Copy>("buildOpenApiSite") {
    dependsOn("build")
    copy {
        from("resources/openapi-site/index.html")
        into(layout.buildDirectory.dir("openapi-site"))
        // magic code for nice frontend :D
        expand("serviceList" to """
            ${openApiProjects.joinToString("") {
                "<li>" +
                    "<a href='./$it'>${it.split("-")
                        .joinToString(" ", transform = String::capitalized)}</a>" +
                "</li>"
            }}
        """.trimIndent())
    }
    openApiProjects.forEach {
        copy {
            from("$it/build/generated/ksp/main/resources/META-INF/swagger/")
            include("*.yml")
            into(layout.buildDirectory.dir("openapi-site/$it"))
            rename { _ -> "$it.yml" }
        }
        copy {
            from("resources/openapi-site/openapi-static.html")
            into(layout.buildDirectory.dir("openapi-site/$it"))
            rename("openapi-static.html", "index.html")
            expand("url" to "./$it.yml")
        }
    }
}
