plugins {
    kotlin("jvm")
    id("com.ncorti.ktfmt.gradle")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.kotlinx.kover")
}

ktfmt {
    kotlinLangStyle()
}

val catalog: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    testImplementation(catalog.getLibrary("konsist"))
    testImplementation(catalog.getLibrary("kotest"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(JavaVersion.asInt)
}
