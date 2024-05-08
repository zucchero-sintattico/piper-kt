plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.servers.ApplicationKt") }

detekt { config.setFrom("./detekt-config.yml") }

dependencies {

    ksp("io.micronaut.openapi:micronaut-openapi")
    compileOnly("io.micronaut.openapi:micronaut-openapi-annotations")
}