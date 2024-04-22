package piperkt.services.users.application

import base.UnitTest
import io.kotest.matchers.shouldBe
import mocks.publishers.MockedUserEventPublisher
import mocks.repositories.InMemoryUserRepository
import org.junit.jupiter.api.assertThrows
import piperkt.services.users.domain.user.User
import piperkt.services.users.domain.user.UserError.UserNotFound
import piperkt.services.users.domain.user.UserEvent.UserUpdated
import piperkt.services.users.domain.user.Username

class UserServiceTest :
    UnitTest.FunSpec({
        val userRepository = InMemoryUserRepository()
        val userEventPublisher = MockedUserEventPublisher()
        val userService = UserService(userRepository, userEventPublisher)

        val username = Username("username")
        val description = "description"
        val profilePicture = ByteArray(0)
        val user =
            User(
                username = username,
                password = "password",
                description = description,
                profilePicture = profilePicture
            )

        beforeEach {
            userRepository.clear()
            userEventPublisher.clear()
            userRepository.save(user)
        }

        test("getUser") {
            val userDTO = userService.getUser(username.value)
            userDTO shouldBe user.toDTO()
        }

        test("getUser throws UserNotFound") {
            assertThrows<UserNotFound> { userService.getUser("nonExistingUsername") }
        }

        test("updateUserDescription") {
            val newDescription = "newDescription"
            userService.updateUserDescription(username.value, newDescription)
            val updatedUser = userRepository.findByUsername(username.value)!!
            updatedUser.description shouldBe newDescription
            userEventPublisher.publishedEvents shouldBe
                listOf(UserUpdated(user.username, description = newDescription))
        }

        test("updateUserProfilePicture") {
            val newProfilePicture = ByteArray(1)
            userService.updateUserProfilePicture(username.value, newProfilePicture)
            val updatedUser = userRepository.findByUsername(username.value)!!
            updatedUser.profilePicture shouldBe newProfilePicture
            userEventPublisher.publishedEvents shouldBe
                listOf(UserUpdated(user.username, profilePicture = newProfilePicture))
        }
    })
