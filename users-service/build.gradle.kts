plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.users.ApplicationKt") }

dependencies {
    implementation(libs.jbcrypt)
    implementation(libs.reactor)
}

detekt { config.from(files("detekt-config.yml")) }
