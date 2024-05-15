import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.servers.ApplicationKt") }

tasks.withType<ShadowJar> {
    isZip64= true
}

detekt { config.setFrom("./detekt-config.yml") }
