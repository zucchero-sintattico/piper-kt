import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.multimedia.ApplicationKt") }

dependencies {
    implementation(libs.socketio.netty)
    implementation(libs.socketio)
}

tasks.withType<ShadowJar> {
    isZip64= true
}

detekt { config.from(files("detekt-config.yml")) }
