package piperkt.services.users.application

import base.UnitTest
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import mocks.publishers.MockedUserEventPublisher
import mocks.repositories.InMemoryUserRepository
import org.junit.jupiter.api.assertThrows
import piperkt.services.users.domain.user.UserError
import piperkt.services.users.domain.user.UserError.UserAlreadyExists
import piperkt.services.users.domain.user.UserError.UserNotFound
import piperkt.services.users.domain.user.Username

class AuthServiceTest :
    UnitTest.FunSpec({
        val userRepository = InMemoryUserRepository()
        val userEventPublisher = MockedUserEventPublisher()
        val authService = AuthService(userRepository, userEventPublisher)

        val existingUsername = Username("existingUsername")
        val existingPassword = "existingPassword"

        beforeEach {
            userRepository.clear()
            userEventPublisher.clear()
            authService.register(existingUsername.value, existingPassword)
        }

        test("registerUser") {
            val username = "username"
            val password = "password"
            val createdUser = authService.register(username, password)
            createdUser.username shouldBe Username(username)
        }

        test("registerUser throws UserAlreadyExists") {
            assertThrows<UserAlreadyExists> {
                authService.register(existingUsername.value, existingPassword)
            }
        }

        test("loginUser") {
            val user = authService.login(existingUsername.value, existingPassword)
            user shouldNotBe null
        }

        test("loginUser throws UserNotFound") {
            assertThrows<UserNotFound> { authService.login("nonExistingUsername", "password") }
        }

        test("loginUser throws InvalidPassword") {
            assertThrows<UserError.InvalidPassword> {
                authService.login(existingUsername.value, "wrongPassword")
            }
        }

        test("saveRefreshToken") {
            authService.saveRefreshToken(existingUsername.value, "refreshToken")
            val user = userRepository.findByUsername(existingUsername.value)!!
            user.refreshToken shouldBe "refreshToken"
        }

        test("saveRefreshToken throws UserNotFound") {
            assertThrows<UserNotFound> {
                authService.saveRefreshToken("nonExistingUsername", "refreshToken")
            }
        }

        test("getUserByRefreshToken") {
            val refreshToken = "refresh"
            authService.saveRefreshToken(existingUsername.value, refreshToken)
            val user = authService.getUserByRefreshToken(refreshToken)
            user.username shouldBe existingUsername
        }

        test("getUserByRefreshToken throws RefreshTokenNotFound") {
            assertThrows<UserError.RefreshTokenNotFound> {
                authService.getUserByRefreshToken("nonExistingRefreshToken")
            }
        }
    })
