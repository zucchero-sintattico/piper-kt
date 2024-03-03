package micronaut.playground

import io.kotest.core.spec.style.AnnotationSpec
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import micronaut.playground.application.api.UserServiceApi

@MicronautTest
class UserControllerTest(private val userServiceApi: UserServiceApi) : AnnotationSpec() {

    @Test fun test() {}
}
