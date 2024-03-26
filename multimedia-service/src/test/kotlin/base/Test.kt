package base

import io.micronaut.test.extensions.kotest5.annotation.MicronautTest

sealed interface Test {

    interface Unit : Test

    @MicronautTest interface Integration : Test
}
