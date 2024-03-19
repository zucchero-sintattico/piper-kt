plugins { id("micronaut-base") }

application { mainClass.set("piperkt.service.friendship.ApplicationKt") }

dependencies {
    ksp("io.micronaut.data:micronaut-data-document-processor")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    runtimeOnly("org.mongodb:mongodb-driver-sync")
    aotPlugins("io.micronaut.security:micronaut-security-aot")
    ksp("io.micronaut.security:micronaut-security-annotations")
    implementation("io.micronaut.security:micronaut-security-jwt")

}
