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
    testImplementation ("io.cucumber:cucumber-java:7.15.0")
    testImplementation ("io.cucumber:cucumber-junit:7.15.0")
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}