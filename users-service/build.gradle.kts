plugins {
    id("micronaut-base")
    id("java-test-fixtures")
}

application { mainClass.set("piperkt.services.users.ApplicationKt") }

dependencies {
    implementation(project(":commons"))
    testImplementation(testFixtures(project(":commons")))
    ksp("io.micronaut.data:micronaut-data-document-processor")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    runtimeOnly("org.mongodb:mongodb-driver-sync")
    // Kafka
    implementation("io.micronaut.kafka:micronaut-kafka")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.3.1")
    implementation("org.mindrot:jbcrypt:0.4")
    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.projectreactor:reactor-core:3.6.5")
    implementation("io.micronaut.flyway:micronaut-flyway")
}

detekt { config.from(files("detekt-config.yml")) }
