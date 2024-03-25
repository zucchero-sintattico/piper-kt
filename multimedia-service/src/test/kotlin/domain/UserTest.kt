package domain

import base.Test
import io.kotest.matchers.shouldBe
import piperkt.services.multimedia.domain.User
import piperkt.services.multimedia.domain.Username

class UserTest :
    Test.Unit.FunSpec({
        context("a User") {
            val username = Username("UserId")

            test("should be able to create a user given an Username") {
                val user = User(username)
                user.username shouldBe username
            }

            test("should be able to create a user given a string username") {
                val user = User.fromUsername(username.value)
                user.username shouldBe username
            }
        }

        context("two Users") {
            val username = Username("UserId")
            val user1 = User(username)
            val user2 = User(username)

            test("should be equal if their usernames are equal") { user1 shouldBe user2 }
        }
    })
