plugins {
    id("io.github.zucchero-sintattico.typescript-gradle-plugin") version "4.2.2"
    alias(libs.plugins.spotless)
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

tasks.register("buildLayers") {
    dependsOn("compileTypescript")
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
