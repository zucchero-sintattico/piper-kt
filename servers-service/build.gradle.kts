plugins { id("micronaut-base") }

application { mainClass.set("micronaut.playground.ApplicationKt") }

dependencies {
    ksp("io.micronaut.data:micronaut-data-document-processor")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    //testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.0")
    runtimeOnly("org.mongodb:mongodb-driver-sync")
}
