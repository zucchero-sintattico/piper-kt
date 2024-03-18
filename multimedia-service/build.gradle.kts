plugins { id("micronaut-base") }

application { mainClass.set("piperkt.services.multimedia.ApplicationKt") }

dependencies {
    ksp("io.micronaut.data:micronaut-data-document-processor")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    runtimeOnly("org.mongodb:mongodb-driver-sync")
    // Kafka
    implementation("io.micronaut.kafka:micronaut-kafka")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
}

detekt { config.from(files("detekt-config.yml")) }
