plugins {
    kotlin("jvm")
    id("com.ncorti.ktfmt.gradle")
    id("io.gitlab.arturbosch.detekt")
}

ktfmt {
    kotlinLangStyle()
}
