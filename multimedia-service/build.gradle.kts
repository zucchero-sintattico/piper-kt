plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.multimedia.ApplicationKt") }

dependencies {
    implementation(libs.socketio.netty)
    implementation(libs.socketio)
    implementation("com.google.code.gson:gson")
}

detekt { config.from(files("detekt-config.yml")) }
