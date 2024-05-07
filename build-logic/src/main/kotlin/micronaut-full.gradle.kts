plugins {
    id("micronaut-base")
}

val catalog: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    ksp(catalog.getLibrary("micronaut-data-document-processor"))
    implementation(catalog.getLibrary("micronaut-data-mongodb"))
    runtimeOnly(catalog.getLibrary("mongodb-driver-sync"))
    implementation(catalog.getLibrary("micronaut-kafka"))
}


