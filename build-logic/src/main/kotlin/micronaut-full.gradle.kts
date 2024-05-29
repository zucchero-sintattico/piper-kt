import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("micronaut-base")
    id("java-test-fixtures")
}

val catalog: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

tasks.withType<ShadowJar> {
    isZip64 = true
}

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
    implementation(project(":events"))
    testImplementation(testFixtures(project(":architecture-tests")))
    // Swagger
    ksp(catalog.getLibrary("micronaut-openapi"))
    compileOnly(catalog.getLibrary("micronaut-openapi-annotations"))
}
