tasks.register("buildLayers") {
    dependsOn("build")
}

tasks.register("dockerfile") {
    doLast {
        copy {
            from("Dockerfile")
            into("build/docker/main")
        }
    }
}
