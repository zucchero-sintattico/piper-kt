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
    implementation(catalog.getLibrary("micronaut-discovery-client"))
    implementation(catalog.getLibrary("micronaut-management"))
    // Commons
    implementation(project(":commons"))
    testImplementation(testFixtures(project(":commons")))

    // Swagger
    ksp(catalog.getLibrary("openapi"))
    compileOnly(catalog.getLibrary("openapi-annotations"))
}