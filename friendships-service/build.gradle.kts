import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.friendships.ApplicationKt") }

tasks.withType<ShadowJar> {
    isZip64= true
}

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

detekt { config.from(files("detekt-config.yml")) }
