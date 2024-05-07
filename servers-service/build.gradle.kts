plugins {
    id("micronaut-full")
    id("java-test-fixtures")
}

application { mainClass.set("piperkt.services.servers.ApplicationKt") }

detekt { config.setFrom("./detekt-config.yml") }

dependencies {
    testImplementation(testFixtures(project(":commons")))
    implementation(project(":commons"))
    implementation("io.micronaut.security:micronaut-security-jwt")
}
