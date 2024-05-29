plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.multimedia.ApplicationKt") }

dependencies {
    implementation(libs.socketio.netty)
    implementation(libs.socketio)
}

detekt { config.from(files("detekt-config.yml")) }
