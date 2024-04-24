plugins {
    id("micronaut-base")
    id("java-test-fixtures")
}

application { mainClass.set("piperkt.services.servers.ApplicationKt") }

dependencies {
    ksp("io.micronaut.data:micronaut-data-document-processor")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    testImplementation(testFixtures(project(":commons")))
    runtimeOnly("org.mongodb:mongodb-driver-sync")
    implementation(project(":commons"))
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.1")
    // Kafka
    implementation("io.micronaut.kafka:micronaut-kafka")
    // Jwt
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.micronaut:micronaut-retry")
}
