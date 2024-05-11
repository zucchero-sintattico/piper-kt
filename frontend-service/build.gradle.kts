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
