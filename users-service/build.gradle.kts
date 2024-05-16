import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.users.ApplicationKt") }

dependencies {
    implementation(libs.jbcrypt)
    implementation(libs.reactor)
}

tasks.withType<ShadowJar> {
    isZip64= true
}

detekt { config.from(files("detekt-config.yml")) }
