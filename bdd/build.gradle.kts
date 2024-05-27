plugins {
    id("kotlin-jvm")

}

dependencies {
    testImplementation(platform(libs.cucumber.bom))
    testImplementation(libs.bundles.cucumberJunit)
    testImplementation(libs.junit.platform)
    testImplementation(project(":commons"))
    testImplementation(project(":servers-service"))

}

// https://github.com/cucumber/cucumber-jvm/tree/main/cucumber-junit-platform-engine#gradle
tasks.test {
    useJUnitPlatform()
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
}
