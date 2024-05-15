import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.multimedia.ApplicationKt") }

dependencies {
    implementation("com.corundumstudio.socketio:netty-socketio:2.0.9")
    implementation("io.socket:socket.io-client:2.1.0")
}

tasks.withType<ShadowJar> {
    isZip64= true
}

detekt { config.from(files("detekt-config.yml")) }
