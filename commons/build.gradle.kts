plugins {
    kotlin("multiplatform")
    id("java-test-fixtures")
}

kotlin {
    jvm()
    js {
        nodejs()
    }
}
