plugins {
    id("kotlin-base")
    id("org.jetbrains.kotlin.jvm")
    id("org.jetbrains.kotlin.plugin.allopen")
    id("com.google.devtools.ksp")
    id("com.github.johnrengelman.shadow")
    id("io.micronaut.application")
    id("io.micronaut.aot")
    id("io.micronaut.test-resources")
}

val catalog: VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

dependencies {
    ksp(catalog.getBundle("micronaut-ksp"))
    implementation(catalog.getBundle("micronaut-kotlin"))
    catalog.getLibrary("micronaut-httpClient").let {
        compileOnly(it)
        testImplementation(it)
    }
    testImplementation(catalog.getLibrary("mockito-kotlin"))
    runtimeOnly(catalog.getLibrary("logback"))
    runtimeOnly(catalog.getLibrary("jackson-kotlin"))
    runtimeOnly(catalog.getLibrary("snakeyaml"))
}

java {
    sourceCompatibility = JavaVersion.asVersion
}

micronaut {
    runtime("netty")
    testRuntime("kotest5")
    processing {
        incremental(true)
        annotations("piperkt.*")
    }
    aot {
        // Please review carefully the optimizations enabled below
        // Check https://micronaut-projects.github.io/micronaut-aot/latest/guide/ for more details
        optimizeServiceLoading = false
        convertYamlToJava = false
        precomputeOperations = true
        cacheEnvironment = true
        optimizeClassLoading = true
        deduceEnvironment = true
        optimizeNetty = true
    }

}


tasks.named<io.micronaut.gradle.docker.DockerBuildOptions>("dockerfile") {
    editDockerfile {
        replace(
            """ENTRYPOINT ["java", "-jar", "/home/app/application.jar"]""",
            """ENTRYPOINT ["java", "-Dmicronaut.environments=prod", "-jar", "/home/app/application.jar"]"""
        )
    }
}
