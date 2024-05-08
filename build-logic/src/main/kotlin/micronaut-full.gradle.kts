plugins {
    id("micronaut-base")
    id("java-test-fixtures")
}

val catalog: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    ksp(catalog.getLibrary("micronaut-data-document-processor"))
    implementation(catalog.getLibrary("micronaut-data-mongodb"))
    runtimeOnly(catalog.getLibrary("mongodb-driver-sync"))
    implementation(catalog.getLibrary("micronaut-kafka"))
    implementation(catalog.getLibrary("micronaut-jwt"))
    // Commons
    implementation(project(":commons"))
    testImplementation(testFixtures(project(":commons")))
}