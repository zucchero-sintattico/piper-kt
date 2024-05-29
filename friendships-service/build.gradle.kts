plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.friendships.ApplicationKt") }

detekt { config.from(files("./detekt-config.yml")) }
