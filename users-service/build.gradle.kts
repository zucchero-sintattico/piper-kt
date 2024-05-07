plugins {
    id("micronaut-full")
    id("java-test-fixtures")
}

application { mainClass.set("piperkt.services.users.ApplicationKt") }

dependencies {
    implementation(project(":commons"))
    testImplementation(testFixtures(project(":commons")))
    implementation("org.mindrot:jbcrypt:0.4")
    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.projectreactor:reactor-core:3.6.5")
}

detekt { config.from(files("detekt-config.yml")) }
