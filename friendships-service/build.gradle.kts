plugins {
    id("micronaut-full")
    id("java-test-fixtures")
}

application { mainClass.set("piperkt.services.friendships.ApplicationKt") }

detekt { config.setFrom("./detekt-config.yml") }

dependencies {
    testImplementation(testFixtures(project(":commons")))
    implementation(project(":commons"))
    // Kafka
    implementation("io.micronaut.kafka:micronaut-kafka")
    // Jwt
    implementation("io.micronaut.security:micronaut-security-jwt")
}
