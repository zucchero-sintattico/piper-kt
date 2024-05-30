plugins {
    id("kotlin-jvm")
    id("java-test-fixtures")
}

dependencies {
    implementation(libs.kotest)
    implementation(libs.konsist)
}
