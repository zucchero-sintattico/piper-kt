plugins {

    kotlin("jvm")
    alias(libs.plugins.micronaut.lib)
}

dependencies {
    testImplementation(platform(libs.cucumber.bom))
    testImplementation(libs.bundles.cucumberJunit)
    testImplementation(libs.junit.platform)

    testImplementation(libs.kotest)
    testImplementation(libs.micronaut.httpClient)
    testImplementation(libs.micronaut.security)
    testImplementation(project(":servers-service"))
}

// https://github.com/cucumber/cucumber-jvm/tree/main/cucumber-junit-platform-engine#gradle
tasks.test {
    useJUnitPlatform()
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
}
