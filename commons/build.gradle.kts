plugins {
    id("kotlin-multiplatform")
}

kotlin {
    val javaVersion: String by project
    jvm {
        compilations.all {
            kotlinOptions {
                jvmTarget = javaVersion
            }
        }
    }
    js {
        nodejs()
    }
}
