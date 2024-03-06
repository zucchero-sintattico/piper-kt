package piperkt.services.servers

import io.kotest.core.spec.style.StringSpec
import io.micronaut.runtime.EmbeddedApplication
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

@MicronautTest
class MicronautPlaygroundTest(private val application: EmbeddedApplication<*>) :
    StringSpec({ "test the server is running" { assert(application.isRunning) } })
