plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.users.ApplicationKt") }

dependencies {
    implementation("org.mindrot:jbcrypt:0.4")
    implementation("io.projectreactor:reactor-core:3.6.5")
}

detekt { config.from(files("detekt-config.yml")) }
