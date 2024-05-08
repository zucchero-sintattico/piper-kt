plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.friendships.ApplicationKt") }

detekt { config.setFrom("./detekt-config.yml") }
