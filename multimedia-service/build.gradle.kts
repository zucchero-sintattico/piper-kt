plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.multimedia.ApplicationKt") }

dependencies {
    implementation("com.corundumstudio.socketio:netty-socketio:2.0.9")
    implementation("io.socket:socket.io-client:2.1.0")
}

detekt { config.from(files("detekt-config.yml")) }
