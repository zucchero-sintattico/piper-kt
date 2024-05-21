plugins {
    id("kotlin-multiplatform")
    alias(libs.plugins.npmPublish)
}

dependencies {
    commonMainImplementation(project(":commons"))
}

kotlin {
    sourceSets {
        all {
            languageSettings {
                optIn("kotlin.js.ExperimentalJsExport")
            }
        }
    }

    jvm()
    js(IR) {
        nodejs()
        binaries.library()
        generateTypeScriptDefinitions()
    }
}

npmPublish {
    organization = "zucchero-sintattico"
    registries {
        register("npmjs") {
            uri.set("https://registry.npmjs.org")
            authToken.set(System.getenv("NPM_TOKEN"))
        }
    }
}
