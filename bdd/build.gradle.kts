plugins {
    kotlin("jvm")
}

group = "org.example"
version = "0.1.0-archeo+3994d4a"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}