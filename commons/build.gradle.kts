plugins {
    id("kotlin-base")
    id("java-test-fixtures")
}

dependencies {
    implementation(libs.kotest)
    implementation(libs.konsist)
}
