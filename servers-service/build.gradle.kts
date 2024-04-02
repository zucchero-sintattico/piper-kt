plugins { id("micronaut-base") }

application { mainClass.set("micronaut.playground.ApplicationKt") }

dependencies {
    ksp("io.micronaut.data:micronaut-data-document-processor")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    runtimeOnly("org.mongodb:mongodb-driver-sync")
    implementation(project(":commons"))
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    // Kafka
    implementation("io.micronaut.kafka:micronaut-kafka")
    // Jwt
    implementation("io.micronaut.security:micronaut-security-jwt")
}
