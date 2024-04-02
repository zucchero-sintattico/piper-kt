package domain

import base.Test
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import piperkt.services.multimedia.domain.user.User
import piperkt.services.multimedia.domain.user.UserId

class UserTest :
    Test.Unit,
    FunSpec({
        context("a User") {
            test("should be able to create a user given an UserId") {
                val username = "username"
                val userId = UserId(username)
                val user = User(userId)
                user.id shouldBe userId
                user.username shouldBe username
            }
        }

        context("two Users") {
            val username = "username"
            val userID = UserId(username)
            val user1 = User(userID)
            val user2 = User(userID)

            test("should be equal if their usernames are equal") { user1 shouldBe user2 }
        }
    })