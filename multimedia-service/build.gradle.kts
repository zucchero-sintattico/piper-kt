plugins {
    id("micronaut-full")
    id("java-test-fixtures")
}

application { mainClass.set("piperkt.services.multimedia.ApplicationKt") }

dependencies {
    implementation(project(":commons"))
    testImplementation(testFixtures(project(":commons")))
    implementation("com.corundumstudio.socketio:netty-socketio:2.0.9")
    implementation("io.socket:socket.io-client:2.1.0")
    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut.security:micronaut-security-jwt")
}

detekt { config.from(files("detekt-config.yml")) }
