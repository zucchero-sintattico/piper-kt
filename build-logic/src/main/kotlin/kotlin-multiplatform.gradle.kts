plugins {
    kotlin("multiplatform")
    id("kotlin-conventions")
}

val javaVersion: String by project
kotlin {
    jvmToolchain(javaVersion.toInt())
}
