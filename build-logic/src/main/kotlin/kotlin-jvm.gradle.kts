plugins {
    kotlin("jvm")
    id("kotlin-conventions")
}

val catalog: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    testImplementation(catalog.getLibrary("konsist"))
    testImplementation(catalog.getLibrary("kotest"))
}

tasks.test {
    useJUnitPlatform()
}

val javaVersion: String by project
kotlin {
    jvmToolchain(javaVersion.toInt())
}
