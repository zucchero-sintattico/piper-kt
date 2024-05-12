import io.github.zuccherosintattico.gradle.BuildCommandExecutable

plugins {
    id("io.github.zucchero-sintattico.typescript-gradle-plugin") version "4.3.0"
    id("non-micronaut-project")
    alias(libs.plugins.spotless)
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
