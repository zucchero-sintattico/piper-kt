package piperkt.services.multimedia.domain

import base.UnitTest
import io.kotest.matchers.shouldBe
import piperkt.services.multimedia.domain.user.User
import piperkt.services.multimedia.domain.user.Username

class UserTest :
    UnitTest.FunSpec({
        context("a User") {
            test("should be able to create a user given an UserId") {
                val username = "username"
                val userId = Username(username)
                val user = User(userId)
                user.id shouldBe userId
                user.username shouldBe username
            }
        }

        context("two Users") {
            val username = "username"
            val userID = Username(username)
            val user1 = User(userID)
            val user2 = User(userID)

            test("should be equal if their usernames are equal") { user1 shouldBe user2 }
        }
    })
