import com.lordcodes.turtle.shellRun
import io.github.zuccherosintattico.utils.NodeCommandsExtension.npmCommand

plugins {
    alias(libs.plugins.spotless)
    alias(libs.plugins.typescript)
    id("non-micronaut-project")
}

typescript {
    entrypoint = "app.js"
}

node {
    shouldInstall = true
    version = "22.1.0"
}

spotless {
    typescript {
        target("**/*.ts")
        targetExclude("**/build/**", "**/node_modules/**")
        prettier()
    }
}

tasks.named("npmDependencies") {
    // events package generate js library for notifications -- should be brought in
    dependsOn(":events:jsNodeProductionLibraryDistribution")
    doFirst {
        copy {
            from("../events/build/dist/js/productionLibrary")
            into(layout.buildDirectory.dir("piper-kt-events"))
        }
    }
}

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
        copy {
            from(layout.buildDirectory.dir("piper-kt-events"))
            into("build/docker/main/build/piper-kt-events")
        }
    }
}

// This is a patch, the typescript plugin can handle it
tasks.named("check") {
    dependsOn("compileTypescript")
    doLast {
        runCatching {
            shellRun(project.projectDir) {
                npmCommand(project, "run", "type-checking")
            }
        }
            .onFailure { logger.error(it.stackTraceToString()) }
    }
}