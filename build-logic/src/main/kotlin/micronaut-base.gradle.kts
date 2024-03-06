plugins {
    id("kotlin-base")
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
    runtimeOnly(catalog.getLibrary("logback"))
    runtimeOnly(catalog.getLibrary("jackson-kotlin"))
}

java { sourceCompatibility = JavaVersion.asVersion }

graalvmNative.toolchainDetection.set(false)

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
