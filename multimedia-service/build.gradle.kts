plugins {
    id("micronaut-base")
    id("java-test-fixtures")
    // kotlin("plugin.serialization") version "1.9.23"
}

application { mainClass.set("piperkt.services.multimedia.ApplicationKt") }

dependencies {
    implementation(project(":commons"))
    testImplementation(testFixtures(project(":commons")))
    ksp("io.micronaut.data:micronaut-data-document-processor")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    runtimeOnly("org.mongodb:mongodb-driver-sync")
    // Kafka
    implementation("io.micronaut.kafka:micronaut-kafka")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.2.1")
    implementation("com.corundumstudio.socketio:netty-socketio:2.0.9")
    implementation("io.socket:socket.io-client:2.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")
    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut.security:micronaut-security-jwt")
}

detekt { config.from(files("detekt-config.yml")) }
