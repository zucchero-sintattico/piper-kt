package piperkt.services.multimedia.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.micronaut.test.extensions.kotest5.annotation.MicronautTest
import piperkt.services.multimedia.domain.users.User
import piperkt.services.multimedia.domain.users.UserId

@MicronautTest
class UserTest :
    FunSpec({
        test("a user should have a username") {
            val user = User(UserId("username"))
            user.username.value shouldBe "username"
        }
    })
