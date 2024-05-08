plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.servers.ApplicationKt") }

detekt { config.setFrom("./detekt-config.yml") }