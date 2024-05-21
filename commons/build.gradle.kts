plugins {
    id("kotlin-multiplatform")
}

kotlin {
    jvm()
    js {
        nodejs()
    }
}
