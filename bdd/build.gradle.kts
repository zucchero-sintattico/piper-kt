plugins {
    id("micronaut-full")
}

application { mainClass.set("piperkt.services.bdd.ApplicationKt") }

dependencies {
    testImplementation(platform(libs.cucumber.bom))
    testImplementation(libs.bundles.cucumberJunit)
    testImplementation(libs.junit.platform)
    testImplementation(libs.kotest)
    // testImplementation(project(":servers-service"))
}


// https://github.com/cucumber/cucumber-jvm/tree/main/cucumber-junit-platform-engine#gradle
tasks.test {
    useJUnitPlatform()
    systemProperty("cucumber.junit-platform.naming-strategy", "long")
    /*onlyIf {
        project.hasProperty("bdd")
    }*/
}
