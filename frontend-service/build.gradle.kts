import com.lordcodes.turtle.shellRun
import io.github.zuccherosintattico.gradle.BuildCommandExecutable
import io.github.zuccherosintattico.utils.NodeCommandsExtension.npmCommand

plugins {
    alias(libs.plugins.spotless)
    alias(libs.plugins.typescript)
    id("non-micronaut-project")
}

typescript {
    buildCommandExecutable = BuildCommandExecutable.NPM
    buildCommand = "run build"
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

tasks.named("buildLayers") {
    doLast {
        copy {
            from("dist")
            into("build/docker/main/dist")
        }
        copy {
            from("node_modules")
            into("build/docker/main/node_modules")
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
