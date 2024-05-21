plugins {
    kotlin("multiplatform")
    id("com.ncorti.ktfmt.gradle")
    id("io.gitlab.arturbosch.detekt")
    id("org.jetbrains.kotlinx.kover")
}

ktfmt {
    kotlinLangStyle()
}

val catalog: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

kotlin {
    jvmToolchain(JavaVersion.asInt)
}
